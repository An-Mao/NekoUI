package dev.anye.mc.nekoui.screen.widget;

import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.logging.LogUtils;
import dev.anye.core.color._ColorSupport;
import dev.anye.core.math._MathCDT;
import dev.anye.mc.cores.dt.IOnPress;
import dev.anye.mc.cores.render.GuiGraphicsHelper;
import dev.anye.mc.cores.screen.widget.SimpleListBoxData;
import dev.anye.mc.nekoui.config.Configs;
import dev.anye.mc.nekoui.dat_type.MenuPageData;
import dev.anye.mc.nekoui.register.MenuProject;
import dev.anye.mc.nekoui.register.Registers;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import org.joml.Matrix3x2fStack;
import org.jspecify.annotations.NonNull;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class CircularNewMenu extends CircularMenu {
	private static final Logger LOGGER = LogUtils.getLogger();
	protected final List<RenderPageData> renderPageData;
	protected int nowPage = 0;
	protected int maxPage;

	public CircularNewMenu(
			int x,
			int y,
			int w,
			int h,
			Component message,
			List<RenderPageData> renderPageData
	) {
		super(x, y, w, h, message);
		this.renderPageData = renderPageData;
		maxPage = renderPageData.size();
		setNowPage(0);


		setFlipMode(FlipMode.TIRE);
		setFanTextInnerSpace(10);
	}

	public int getNowPage() {
		return nowPage;
	}

	public void setNowPage(int newPage) {
		if (newPage < 0) {
			this.nowPage = 0;
		} else if (newPage >= maxPage) {
			this.nowPage = maxPage - 1;
		} else {
			this.nowPage = newPage;
		}
		setSectors(getNowPageData().projectNumber());
		this.innerRadius = getNowPageData().innerRadius();
		this.outerRadius = getNowPageData().outerRadius();
	}


	public MenuPageData getNowPageData() {
		return renderPageData.get(getNowPage()).pageData();
	}

	public List<SimpleListBoxData> getNowListBoxData() {
		return renderPageData.get(getNowPage()).projects();
	}

	public void changePage(boolean add) {
		if (add) {
			if (nowPage < maxPage - 1) {
				setNowPage(nowPage + 1);
			} else if (nowPage == maxPage - 1) {
				setNowPage(0);
			}
		} else {
			if (nowPage > 0) {
				setNowPage(nowPage - 1);
			} else if (nowPage == 0) {
				setNowPage(maxPage - 1);
			}

		}
	}

/*
	public void setFanTextInnerSpace(int fanTextInnerSpace) {
		this.fanTextInnerSpace = fanTextInnerSpace;
	}

	public void setFlipMode(FlipMode flipMode) {
		this.flipMode = flipMode;
	}


	public void setFanArc(double fanArc) {
		this.fanArc = fanArc;
		setHalfFanArc(fanArc / 2d);
	}

	public void setHalfFanArc(double halfFanArc) {
		this.halfFanArc = halfFanArc;
	}


	public int getFanTextInnerSpace() {
		return Math.max(fanTextInnerSpace, innerRadius);
	}

	public void setFanAngle(double fanAngle) {
		this.fanAngle = fanAngle;
	}


	public void setSectors(int sectors) {
		this.sectors = sectors;
		setFanAngle(360d / sectors);
		setFanArc(_MathCDT.TWICE_PI / sectors);
	}
	public SimpleListBoxData getData() {
		return getData(index);
	}*/

	public SimpleListBoxData getData(int index) {
		if (isValidIndex(index)) {
			return getNowListBoxData().get(index);
		}
		return null;
	}
/*

	public boolean isValidIndex() {
		return isValidIndex(index);
	}
*/

	public boolean isValidIndex(int index) {
		return index >= 0 && index < getNowListBoxData().size();
	}
/*

	@Override
	public boolean mouseClicked(MouseButtonEvent mouseButtonEvent, boolean doubleClick) {
		return mouseClicked(mouseButtonEvent.x(), mouseButtonEvent.y(), mouseButtonEvent.button()) || super.mouseClicked(mouseButtonEvent, doubleClick);
	}
*/

	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int button) {
		if (this.active && this.visible && this.flipMode == FlipMode.BUTTON) {
			if (button == InputConstants.MOUSE_BUTTON_LEFT) {
				changePage(false);
				return true;
			} else if (button == InputConstants.MOUSE_BUTTON_RIGHT) {
				changePage(true);
				return true;
			}
		}
		return false;
	}

	/*
	@Override
	public void onClick(double pMouseX, double pMouseY, int button) {
		if (flipMode == FlipMode.TIRE) {
			super.onClick(pMouseX, pMouseY, button);
		}else mouseClicked(pMouseX, pMouseY, button);
	}
	@Override
	public void onClick(MouseButtonEvent mouseButtonEvent, boolean doubleClick) {
		onClick(mouseButtonEvent.x(), mouseButtonEvent.y());
	}

	public void onClick(double pMouseX, double pMouseY) {
		if (flipMode == FlipMode.tire) {
			SimpleListBoxData listBoxData = getData();
			if (listBoxData != null) {
				listBoxData.onPress(listBoxData.value());
			}
		}
	}*/

	@Override
	public boolean mouseScrolled(double pMouseX, double pMouseY, double pScrollX, double pScrollY) {
		if (this.flipMode == FlipMode.TIRE) {
			changePage(pScrollY <= 0);
			return true;
		}
		return super.mouseScrolled(pMouseX, pMouseY, pScrollX, pScrollY);
	}

	public static boolean inCircle(int cx,int cy, int x,int y,int r){
		int dx = x - cx;
		double dy = (double)y - cy;
		return Math.sqrt(dx * dx + dy * dy) <= r;
	}

	public boolean isAtInnerRadius(int centerX, int centerY, int mouseX, int mouseY){
		return innerRadius > 0 && inCircle(centerX,centerY,mouseX,mouseY,innerRadius);
	}

	@Override
	protected void extractWidgetRenderState(@NonNull GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float v) {
		if (visible) {
			int centerX = getX();
			int centerY = getY();
			double angle = Math.atan2(mouseY - centerY, mouseX - centerX) + halfFanArc;
			if (angle < 0) {
				angle += _MathCDT.TWICE_PI;
			}

			if (getNowPageData() == null) return;
			guiGraphics.text(this.font, getNowPageData().title(), centerX - this.font.width(getNowPageData().title()) / 2, 6, getTextUsualColor(), false);
			for (int i = 0; i < sectors; i++) {
				double startAngle = i * fanArc;
				double endAngle = (i + 1) * fanArc;
				MenuPageData.ProjectInfo projectInfo = null;
				if (i < getNowPageData().projects().size()) projectInfo = getNowPageData().projects().get(i);
				if (projectInfo == null) {
					if (isAtInnerRadius(centerX,centerY,mouseX,mouseY) &&  angle >= startAngle && angle < endAngle) {
						this.index = -1;
					}
					continue;
				} else if (projectInfo.key().isEmpty() || projectInfo.key().equals("-empty-")) continue;
				String tColor = projectInfo.backgroundNormalColor().isEmpty() ? "auto" : projectInfo.backgroundNormalColor();
				int bgc = tColor.equals("auto") ? getBackgroundUsualColor() : _ColorSupport.HexToColor(tColor);
				tColor = projectInfo.textNormalColor().isEmpty() ? "auto" : projectInfo.textNormalColor();
				int tc = tColor.equals("auto") ? getTextUsualColor() : _ColorSupport.HexToColor(tColor);
				float size = 1;
				if (angle >= startAngle && angle < endAngle) {

					tColor = projectInfo.backgroundHighlightColor().isEmpty() ? "auto" : projectInfo.backgroundHighlightColor();
					bgc = tColor.equals("auto") ? getBackgroundHoverColor() : _ColorSupport.HexToColor(tColor);

					tColor = projectInfo.textHighlightColor().isEmpty() ? "auto" : projectInfo.textHighlightColor();
					tc = tColor.equals("auto") ? getTextHoverColor() : _ColorSupport.HexToColor(tColor);
					//bgc = projectData.getBackgroundHighlightColor();
					//tc = projectData.getTextHighlightColor();
					size = 1.3f;
					this.index = i;
				}
				Matrix3x2fStack poseStack = guiGraphics.pose();
				poseStack.pushMatrix();
				poseStack.translate(centerX, centerY);
				//poseStack.rotate((float) startAngle);
				//poseStack.mulPose(Axis.ZP.rotation((float) (startAngle)));
				GuiGraphicsHelper.SectorX(guiGraphics, centerX, centerY, innerRadius, outerRadius, startAngle - halfFanArc, endAngle - halfFanArc, bgc);
				if (isValidIndex(i)) {
					SimpleListBoxData boxData = getData(i);
					String name = "";
					if (boxData != null) {
						name = boxData.name().getString();
					}
					drawName(guiGraphics, startAngle, name, tc, size);
				}
				poseStack.popMatrix();
			}
		}
	}

	public record RenderPageData(MenuPageData pageData, List<SimpleListBoxData> projects) {
		public static RenderPageData create(MenuPageData pageData, IOnPress func) {
			List<SimpleListBoxData> data = new ArrayList<>();
			pageData.projects().forEach(projectData -> {
				MenuProject menuData = Configs.MENU_PROJECTS.get(projectData.key());
				if (menuData != null) {
					data.add(new SimpleListBoxData(Component.literal(menuData.name()), projectData.key(), func));
				} else {
					Registers.MENU_PROJECT.registry().get(Identifier.parse(projectData.key())).ifPresent(holder -> data.add(new SimpleListBoxData(Component.literal(holder.value().name()), projectData.key(), func)));
				}
			});
			return new RenderPageData(pageData, data);
		}
	}
}
