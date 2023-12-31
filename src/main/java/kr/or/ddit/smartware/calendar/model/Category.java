package kr.or.ddit.smartware.calendar.model;

public class Category {

	private String category_id;	// 카테고리 아이디
	private String category_nm; // 카테고리 이름
	private String color;       // 색상
	private String emp_id;
	
	public Category() {	}

	public Category(String category_id, String category_nm, String color, String emp_id) {
		this.category_id = category_id;
		this.category_nm = category_nm;
		this.emp_id = emp_id;
		this.color = color;
	}

	public String getCategory_id() {
		return category_id;
	}
	public void setCategory_id(String category_id) {
		this.category_id = category_id;
	}
	public String getCategory_nm() {
		return category_nm;
	}
	public void setCategory_nm(String category_nm) {
		this.category_nm = category_nm;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getEmp_id() {
		return emp_id;
	}
	public void setEmp_id(String emp_id) {
		this.emp_id = emp_id;
	}

	@Override
	public String toString() {
		return "Category [category_id=" + category_id + ", category_nm=" + category_nm + ", color=" + color
				+ ", emp_id=" + emp_id + "]";
	}
	
}
