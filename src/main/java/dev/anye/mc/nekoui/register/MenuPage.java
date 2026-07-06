package dev.anye.mc.nekoui.register;

import dev.anye.mc.nekoui.dat$type.MenuPageData;

import java.util.List;

public class MenuPage {
	private MenuPageData menuPageData;

	public MenuPage(MenuPageData menuPageData) {
		this.menuPageData = menuPageData;
	}

	public MenuPage(String title, int projectNumber, int innerRadius, int outerRadius, MenuPageData.ProjectInfo... projects) {

		this(title, projectNumber, innerRadius, outerRadius, List.of(projects));
	}

	public MenuPage(String title, int projectNumber, int innerRadius, int outerRadius, List<MenuPageData.ProjectInfo> projects) {
		this(new MenuPageData(title, projectNumber, innerRadius, outerRadius, projects));
	}

	public String title() {
		return menuPageData.title();
	}

	public int projectNumber() {
		return menuPageData.projectNumber();
	}

	public int innerRadius() {
		return menuPageData.innerRadius();
	}

	public int outerRadius() {
		return menuPageData.outerRadius();
	}

	public List<MenuPageData.ProjectInfo> projects() {
		return menuPageData.projects();
	}

	public MenuPageData getData() {
		return menuPageData;
	}
}
