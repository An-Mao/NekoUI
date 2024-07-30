package anmao.mc.nekoui.screen.widget;

import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.narration.NarratedElementType;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.Mth;
import net.minecraft.util.StringUtil;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class SimpleEditBox extends SimpleWidgetCore<SimpleEditBox>{
    private String value = "";
    private int maxLength = 32;
    private boolean bordered = true;
    private boolean canLoseFocus = true;
    private boolean isEditable = true;
    private int displayPos;
    private int cursorPos;
    private int highlightPos;
    @Nullable
    private String suggestion;
    @Nullable
    private Consumer<String> responder;
    private Predicate<String> filter = Objects::nonNull;
    private BiFunction<String, Integer, FormattedCharSequence> formatter = (p_94147_, p_94148_) -> FormattedCharSequence.forward(p_94147_, Style.EMPTY);
    @Nullable
    private Component hint;
    private long focusedTime = Util.getMillis();

    public SimpleEditBox(int pWidth, int pHeight, Component pMessage) {
        this(0, 0, pWidth, pHeight, pMessage);
    }

    public SimpleEditBox( int pX, int pY, int pWidth, int pHeight, Component pMessage) {
        this(pX, pY, pWidth, pHeight, null, pMessage);
    }

    public SimpleEditBox(int pX, int pY, int pWidth, int pHeight, @Nullable SimpleEditBox pEditBox, Component pMessage) {
        super(pX, pY, pWidth, pHeight, pMessage);
        if (pEditBox != null) {
            this.setValue(pEditBox.getValue());
        }
    }

    public void setResponder(Consumer<String> pResponder) {
        this.responder = pResponder;
    }

    public void setFormatter(BiFunction<String, Integer, FormattedCharSequence> pTextFormatter) {
        this.formatter = pTextFormatter;
    }

    @Override
    protected MutableComponent createNarrationMessage() {
        Component component = this.getMessage();
        return Component.translatable("gui.narrate.editBox", component, this.value);
    }

    public void setValue(String pText) {
        if (this.filter.test(pText)) {
            if (pText.length() > this.maxLength) {
                this.value = pText.substring(0, this.maxLength);
            } else {
                this.value = pText;
            }

            this.moveCursorToEnd(false);
            this.setHighlightPos(this.cursorPos);
            this.onValueChange(pText);
        }
    }

    public String getValue() {
        return this.value;
    }

    public String getHighlighted() {
        int i = Math.min(this.cursorPos, this.highlightPos);
        int j = Math.max(this.cursorPos, this.highlightPos);
        return this.value.substring(i, j);
    }

    public void setFilter(Predicate<String> pValidator) {
        this.filter = pValidator;
    }

    public void insertText(String pTextToWrite) {
        int i = Math.min(this.cursorPos, this.highlightPos);
        int j = Math.max(this.cursorPos, this.highlightPos);
        int k = this.maxLength - this.value.length() - (i - j);
        if (k > 0) {
            String s = StringUtil.filterText(pTextToWrite);
            int l = s.length();
            if (k < l) {
                if (Character.isHighSurrogate(s.charAt(k - 1))) {
                    k--;
                }

                s = s.substring(0, k);
                l = k;
            }

            String s1 = new StringBuilder(this.value).replace(i, j, s).toString();
            if (this.filter.test(s1)) {
                this.value = s1;
                this.setCursorPosition(i + l);
                this.setHighlightPos(this.cursorPos);
                this.onValueChange(this.value);
            }
        }
    }

    private void onValueChange(String pNewText) {
        if (this.responder != null) {
            this.responder.accept(pNewText);
        }
    }

    private void deleteText(int pCount) {
        if (Screen.hasControlDown()) {
            this.deleteWords(pCount);
        } else {
            this.deleteChars(pCount);
        }
    }

    public void deleteWords(int pNum) {
        if (!this.value.isEmpty()) {
            if (this.highlightPos != this.cursorPos) {
                this.insertText("");
            } else {
                this.deleteCharsToPos(this.getWordPosition(pNum));
            }
        }
    }

    public void deleteChars(int pNum) {
        this.deleteCharsToPos(this.getCursorPos(pNum));
    }

    public void deleteCharsToPos(int pNum) {
        if (!this.value.isEmpty()) {
            if (this.highlightPos != this.cursorPos) {
                this.insertText("");
            } else {
                int i = Math.min(pNum, this.cursorPos);
                int j = Math.max(pNum, this.cursorPos);
                if (i != j) {
                    String s = new StringBuilder(this.value).delete(i, j).toString();
                    if (this.filter.test(s)) {
                        this.value = s;
                        this.moveCursorTo(i, false);
                    }
                }
            }
        }
    }

    public int getWordPosition(int pNumWords) {
        return this.getWordPosition(pNumWords, this.getCursorPosition());
    }

    private int getWordPosition(int pNumWords, int pPos) {
        return this.getWordPosition(pNumWords, pPos, true);
    }

    private int getWordPosition(int pNumWords, int pPos, boolean pSkipConsecutiveSpaces) {
        int i = pPos;
        boolean flag = pNumWords < 0;
        int j = Math.abs(pNumWords);

        for (int k = 0; k < j; k++) {
            if (!flag) {
                int l = this.value.length();
                i = this.value.indexOf(32, i);
                if (i == -1) {
                    i = l;
                } else {
                    while (pSkipConsecutiveSpaces && i < l && this.value.charAt(i) == ' ') {
                        i++;
                    }
                }
            } else {
                while (pSkipConsecutiveSpaces && i > 0 && this.value.charAt(i - 1) == ' ') {
                    i--;
                }

                while (i > 0 && this.value.charAt(i - 1) != ' ') {
                    i--;
                }
            }
        }

        return i;
    }

    public void moveCursor(int pDelta, boolean pSelect) {
        this.moveCursorTo(this.getCursorPos(pDelta), pSelect);
    }

    private int getCursorPos(int pDelta) {
        return Util.offsetByCodepoints(this.value, this.cursorPos, pDelta);
    }

    public void moveCursorTo(int pDelta, boolean pSelect) {
        this.setCursorPosition(pDelta);
        if (!pSelect) {
            this.setHighlightPos(this.cursorPos);
        }

        this.onValueChange(this.value);
    }

    public void setCursorPosition(int pPos) {
        this.cursorPos = Mth.clamp(pPos, 0, this.value.length());
        this.scrollTo(this.cursorPos);
    }

    public void moveCursorToStart(boolean pSelect) {
        this.moveCursorTo(0, pSelect);
    }

    public void moveCursorToEnd(boolean pSelect) {
        this.moveCursorTo(this.value.length(), pSelect);
    }

    @Override
    public boolean keyPressed(int pKeyCode, int pScanCode, int pModifiers) {
        if (this.isActive() && this.isFocused()) {
            switch (pKeyCode) {
                case 259:
                    if (this.isEditable) {
                        this.deleteText(-1);
                    }

                    return true;
                case 260:
                case 264:
                case 265:
                case 266:
                case 267:
                default:
                    if (Screen.isSelectAll(pKeyCode)) {
                        this.moveCursorToEnd(false);
                        this.setHighlightPos(0);
                        return true;
                    } else if (Screen.isCopy(pKeyCode)) {
                        Minecraft.getInstance().keyboardHandler.setClipboard(this.getHighlighted());
                        return true;
                    } else if (Screen.isPaste(pKeyCode)) {
                        if (this.isEditable()) {
                            this.insertText(Minecraft.getInstance().keyboardHandler.getClipboard());
                        }

                        return true;
                    } else {
                        if (Screen.isCut(pKeyCode)) {
                            Minecraft.getInstance().keyboardHandler.setClipboard(this.getHighlighted());
                            if (this.isEditable()) {
                                this.insertText("");
                            }

                            return true;
                        }

                        return false;
                    }
                case 261:
                    if (this.isEditable) {
                        this.deleteText(1);
                    }

                    return true;
                case 262:
                    if (Screen.hasControlDown()) {
                        this.moveCursorTo(this.getWordPosition(1), Screen.hasShiftDown());
                    } else {
                        this.moveCursor(1, Screen.hasShiftDown());
                    }

                    return true;
                case 263:
                    if (Screen.hasControlDown()) {
                        this.moveCursorTo(this.getWordPosition(-1), Screen.hasShiftDown());
                    } else {
                        this.moveCursor(-1, Screen.hasShiftDown());
                    }

                    return true;
                case 268:
                    this.moveCursorToStart(Screen.hasShiftDown());
                    return true;
                case 269:
                    this.moveCursorToEnd(Screen.hasShiftDown());
                    return true;
            }
        } else {
            return false;
        }
    }

    public boolean canConsumeInput() {
        return this.isActive() && this.isFocused() && this.isEditable();
    }

    @Override
    public boolean charTyped(char pCodePoint, int pModifiers) {
        if (!this.canConsumeInput()) {
            return false;
        } else if (StringUtil.isAllowedChatCharacter(pCodePoint)) {
            if (this.isEditable) {
                this.insertText(Character.toString(pCodePoint));
            }

            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onClick(double pMouseX, double pMouseY) {
        int i = Mth.floor(pMouseX) - this.getX();
        i -= getRadius() * 2 ;
        String s = this.font.plainSubstrByWidth(this.value.substring(this.displayPos), this.getInnerWidth());
        this.moveCursorTo(this.font.plainSubstrByWidth(s, i).length() + this.displayPos, Screen.hasShiftDown());
    }

    @Override
    public void playDownSound(SoundManager pHandler) {
    }
    @Override
    protected void renderContent(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        int textColor = this.isEditable ? getTextUsualColor() : getTextHoverColor();
        int pos = this.cursorPos - this.displayPos;
        String showText = this.font.plainSubstrByWidth(this.value.substring(this.displayPos), getInnerWidth());


        boolean flag = pos >= 0 && pos <= showText.length();
        boolean flag1 = this.isFocused() && (Util.getMillis() - this.focusedTime) / 300L % 2L == 0L && flag;
        int j = this.getContentX() + 2; //this.bordered ? this.getContentX() + 4 : this.getContentX();
        int k = this.getContentY() + getContentH() / 2 - font.lineHeight / 2;  //this.bordered ? this.getContentY() + (this.height - 8) / 2 : this.getContentY();
        int l = j;
        int i1 = Mth.clamp(this.highlightPos - this.displayPos, 0, showText.length());
        if (!showText.isEmpty()) {
            String s1 = flag ? showText.substring(0, pos) : showText;
            l = guiGraphics.drawString(this.font, this.formatter.apply(s1, this.displayPos), j, k, textColor);
        }

        boolean flag2 = this.cursorPos < this.value.length() || this.value.length() >= this.getMaxLength();
        int j1 = l;
        if (!flag) {
            j1 = pos > 0 ? j + this.width : j;
        } else if (flag2) {
            j1 = l - 1;
            l--;
        }

        if (!showText.isEmpty() && flag && pos < showText.length()) {
            guiGraphics.drawString(this.font, this.formatter.apply(showText.substring(pos), this.cursorPos), l, k, textColor);
        }

        if (this.hint != null && showText.isEmpty() && !this.isFocused()) {
            guiGraphics.drawString(this.font, this.hint, l, k, textColor);
        }

        if (!flag2 && this.suggestion != null) {
            guiGraphics.drawString(this.font, this.suggestion, j1 - 1, k, -8355712);
        }

        if (flag1) {
            guiGraphics.fill(RenderType.guiOverlay(), j1, k - 1, j1 + 1, k + 1 + 9, -3092272);
            /*
            if (flag2) {
                guiGraphics.fill(RenderType.guiOverlay(), j1, k - 1, j1 + 1, k + 1 + 9, -3092272);
            } else {
                guiGraphics.drawString(this.font, "_", j1, k, textColor);
            }

             */
        }

        if (i1 != pos) {
            int k1 = j + this.font.width(showText.substring(0, i1));
            this.renderHighlight(guiGraphics, j1, k - 1, k1 - 1, k + 1 + 9);
        }

    }

    private void renderHighlight(GuiGraphics pGuiGraphics, int pMinX, int pMinY, int pMaxX, int pMaxY) {
        if (pMinX < pMaxX) {
            int i = pMinX;
            pMinX = pMaxX;
            pMaxX = i;
        }

        if (pMinY < pMaxY) {
            int j = pMinY;
            pMinY = pMaxY;
            pMaxY = j;
        }

        if (pMaxX > this.getX() + this.width) {
            pMaxX = this.getX() + this.width;
        }

        if (pMinX > this.getX() + this.width) {
            pMinX = this.getX() + this.width;
        }

        pGuiGraphics.fill(RenderType.guiTextHighlight(), pMinX, pMinY, pMaxX, pMaxY, -16776961);
    }

    public void setMaxLength(int pLength) {
        this.maxLength = pLength;
        if (this.value.length() > pLength) {
            this.value = this.value.substring(0, pLength);
            this.onValueChange(this.value);
        }
    }

    private int getMaxLength() {
        return this.maxLength;
    }

    public int getCursorPosition() {
        return this.cursorPos;
    }

    @Override
    public void setFocused(boolean pFocused) {
        if (this.canLoseFocus || pFocused) {
            super.setFocused(pFocused);
            if (pFocused) {
                this.focusedTime = Util.getMillis();
            }
        }
    }

    private boolean isEditable() {
        return this.isEditable;
    }

    public void setEditable(boolean pEnabled) {
        this.isEditable = pEnabled;
    }

    public int getInnerWidth() {
        if (isEditable()){
            return getContentW() - 4 ;
        }
        return  getContentW() - 4 ; //this.width - 8 ;
    }

    public void setHighlightPos(int pPosition) {
        this.highlightPos = Mth.clamp(pPosition, 0, this.value.length());
        this.scrollTo(this.highlightPos);
    }

    private void scrollTo(int pPosition) {
        if (this.font != null) {
            this.displayPos = Math.min(this.displayPos, this.value.length());
            int i = this.getInnerWidth();
            String s = this.font.plainSubstrByWidth(this.value.substring(this.displayPos), i);
            int j = s.length() + this.displayPos;
            if (pPosition == this.displayPos) {
                this.displayPos = this.displayPos - this.font.plainSubstrByWidth(this.value, i, true).length();
            }

            if (pPosition > j) {
                this.displayPos += pPosition - j;
            } else if (pPosition <= this.displayPos) {
                this.displayPos = this.displayPos - (this.displayPos - pPosition);
            }

            this.displayPos = Mth.clamp(this.displayPos, 0, this.value.length());
        }
    }

    public void setCanLoseFocus(boolean pCanLoseFocus) {
        this.canLoseFocus = pCanLoseFocus;
    }

    public boolean isVisible() {
        return this.visible;
    }

    public void setVisible(boolean pIsVisible) {
        this.visible = pIsVisible;
    }

    public void setSuggestion(@Nullable String pSuggestion) {
        this.suggestion = pSuggestion;
    }

    public int getScreenX(int pCharNum) {
        return pCharNum > this.value.length() ? this.getContentX() : this.getContentX() + this.font.width(this.value.substring(0, pCharNum));
    }

    @Override
    public void updateWidgetNarration(NarrationElementOutput pNarrationElementOutput) {
        pNarrationElementOutput.add(NarratedElementType.TITLE, this.createNarrationMessage());
    }

    public void setHint(Component pHint) {
        this.hint = pHint;
    }
}
