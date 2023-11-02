package kr.or.ddit.smartware.calendar.model;

import java.util.Date;

public class Calendar {

	private String cal_id;   	// 일정 아이디
	private String cal_title;	// 일정 제목
	private String cal_cont; 	// 일정 내용
	private Date st_dt;      	// 시작 날짜
	private Date end_dt;	 	// 완료 날짜
	private String allDay;		// 종일
	private String depart_id;	// 부서 아이디
	private String emp_id;		// 사원 아이디
	private String category_id; // 카테고리 아이디
	private String gubun;		// table gubun 값
	
	
	
	//crawling data
	private String bApcoKey             ;
	private String bApcoChannel         ;
	private String bApcoStatus          ;
	private String bApcoNum             ;
	private String bApcoRoom            ;
	private String bApcoCheckInDate     ;
	private String bApcoCheckOutDate    ;
	private String bApcoName            ;
	private String bApcoPerson          ;
	private String bApcoBarbecue        ;
	private String bApcoPayment         ;
	private String bApcoHp              ;
	private String bApcoBooDate         ;
	private String bApcoBooTime         ;
	private String bApcoEtc				;
	
	// room
	private String room_id 				;
	private String roomNm				;
	
	//excel
	private String barbecueStatus		;
	private String bApcoCheckInDateMonth ;
	private String startDate;
	private String endDate;
	private String reservation_date		;
	
	
	
	

	public Calendar() { }
	
	public Calendar(String cal_id, String cal_title, String cal_cont, Date st_dt, Date end_dt, String allDay,
			String depart_id, String emp_id, String category_id) {
		this.cal_id = cal_id;
		this.cal_title = cal_title;
		this.cal_cont = cal_cont;
		this.st_dt = st_dt;
		this.end_dt = end_dt;
		this.allDay = allDay;
		this.depart_id = depart_id;
		this.emp_id = emp_id;
		this.category_id = category_id;
	}
	
	
	
	
	public String getReservation_date() {
		return reservation_date;
	}
	public void setReservation_date(String reservation_date) {
		this.reservation_date = reservation_date;
	}
	public String getbApcoCheckInDateMonth() {
		return bApcoCheckInDateMonth;
	}
	public void setbApcoCheckInDateMonth(String bApcoCheckInDateMonth) {
		this.bApcoCheckInDateMonth = bApcoCheckInDateMonth;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getBarbecueStatus() {
		return barbecueStatus;
	}
	public void setBarbecueStatus(String barbecueStatus) {
		this.barbecueStatus = barbecueStatus;
	}

	public String getGubun() {
		return gubun;
	}
	public void setGubun(String gubun) {
		this.gubun = gubun;
	}

	public String getRoom_id() {
		return room_id;
	}
	public void setRoom_id(String room_id) {
		this.room_id = room_id;
	}

	public String getRoomNm() {
		return roomNm;
	}
	public void setRoomNm(String roomNm) {
		this.roomNm = roomNm;
	}

	public String getCal_id() {
		return cal_id;
	}
	public void setCal_id(String cal_id) {
		this.cal_id = cal_id;
	}
	public String getCal_title() {
		return cal_title;
	}
	public void setCal_title(String cal_title) {
		this.cal_title = cal_title;
	}
	public String getCal_cont() {
		return cal_cont;
	}
	public void setCal_cont(String cal_cont) {
		this.cal_cont = cal_cont;
	}
	public Date getSt_dt() {
		return st_dt;
	}
	public void setSt_dt(Date st_dt) {
		this.st_dt = st_dt;
	}
	public Date getEnd_dt() {
		return end_dt;
	}
	public void setEnd_dt(Date end_dt) {
		this.end_dt = end_dt;
	}
	public String getDepart_id() {
		return depart_id;
	}
	public void setDepart_id(String depart_id) {
		this.depart_id = depart_id;
	}
	public String getEmp_id() {
		return emp_id;
	}
	public void setEmp_id(String emp_id) {
		this.emp_id = emp_id;
	}
	public String getCategory_id() {
		return category_id;
	}
	public void setCategory_id(String category_id) {
		this.category_id = category_id;
	}
	public String getAllDay() {
		return allDay;
	}
	public void setAllDay(String allDay) {
		this.allDay = allDay;
	}
	
	

	/**
	 * @return the bApcoKey
	 */
	public String getbApcoKey() {
		return bApcoKey;
	}

	/**
	 * @param bApcoKey the bApcoKey to set
	 */
	public void setbApcoKey(String bApcoKey) {
		this.bApcoKey = bApcoKey;
	}

	/**
	 * @return the bApcoChannel
	 */
	public String getbApcoChannel() {
		return bApcoChannel;
	}

	/**
	 * @param bApcoChannel the bApcoChannel to set
	 */
	public void setbApcoChannel(String bApcoChannel) {
		this.bApcoChannel = bApcoChannel;
	}

	/**
	 * @return the bApcoStatus
	 */
	public String getbApcoStatus() {
		return bApcoStatus;
	}

	/**
	 * @param bApcoStatus the bApcoStatus to set
	 */
	public void setbApcoStatus(String bApcoStatus) {
		this.bApcoStatus = bApcoStatus;
	}

	/**
	 * @return the bApcoNum
	 */
	public String getbApcoNum() {
		return bApcoNum;
	}

	/**
	 * @param bApcoNum the bApcoNum to set
	 */
	public void setbApcoNum(String bApcoNum) {
		this.bApcoNum = bApcoNum;
	}

	/**
	 * @return the bApcoRoom
	 */
	public String getbApcoRoom() {
		return bApcoRoom;
	}

	/**
	 * @param bApcoRoom the bApcoRoom to set
	 */
	public void setbApcoRoom(String bApcoRoom) {
		this.bApcoRoom = bApcoRoom;
	}

	/**
	 * @return the bApcoCheckInDate
	 */
	public String getbApcoCheckInDate() {
		return bApcoCheckInDate;
	}

	/**
	 * @param bApcoCheckInDate the bApcoCheckInDate to set
	 */
	public void setbApcoCheckInDate(String bApcoCheckInDate) {
		this.bApcoCheckInDate = bApcoCheckInDate;
	}

	/**
	 * @return the bApcoCheckOutDate
	 */
	public String getbApcoCheckOutDate() {
		return bApcoCheckOutDate;
	}

	/**
	 * @param bApcoCheckOutDate the bApcoCheckOutDate to set
	 */
	public void setbApcoCheckOutDate(String bApcoCheckOutDate) {
		this.bApcoCheckOutDate = bApcoCheckOutDate;
	}

	/**
	 * @return the bApcoName
	 */
	public String getbApcoName() {
		return bApcoName;
	}

	/**
	 * @param bApcoName the bApcoName to set
	 */
	public void setbApcoName(String bApcoName) {
		this.bApcoName = bApcoName;
	}

	/**
	 * @return the bApcoPerson
	 */
	public String getbApcoPerson() {
		return bApcoPerson;
	}

	/**
	 * @param bApcoPerson the bApcoPerson to set
	 */
	public void setbApcoPerson(String bApcoPerson) {
		this.bApcoPerson = bApcoPerson;
	}

	/**
	 * @return the bApcoBarbecue
	 */
	public String getbApcoBarbecue() {
		return bApcoBarbecue;
	}

	/**
	 * @param bApcoBarbecue the bApcoBarbecue to set
	 */
	public void setbApcoBarbecue(String bApcoBarbecue) {
		this.bApcoBarbecue = bApcoBarbecue;
	}

	/**
	 * @return the bApcoPayment
	 */
	public String getbApcoPayment() {
		return bApcoPayment;
	}

	/**
	 * @param bApcoPayment the bApcoPayment to set
	 */
	public void setbApcoPayment(String bApcoPayment) {
		this.bApcoPayment = bApcoPayment;
	}

	/**
	 * @return the bApcoHp
	 */
	public String getbApcoHp() {
		return bApcoHp;
	}

	/**
	 * @param bApcoHp the bApcoHp to set
	 */
	public void setbApcoHp(String bApcoHp) {
		this.bApcoHp = bApcoHp;
	}

	/**
	 * @return the bApcoBooDate
	 */
	public String getbApcoBooDate() {
		return bApcoBooDate;
	}

	/**
	 * @param bApcoBooDate the bApcoBooDate to set
	 */
	public void setbApcoBooDate(String bApcoBooDate) {
		this.bApcoBooDate = bApcoBooDate;
	}

	/**
	 * @return the bApcoBooTime
	 */
	public String getbApcoBooTime() {
		return bApcoBooTime;
	}

	/**
	 * @param bApcoBooTime the bApcoBooTime to set
	 */
	public void setbApcoBooTime(String bApcoBooTime) {
		this.bApcoBooTime = bApcoBooTime;
	}

	/**
	 * @return the bApcoEtc
	 */
	public String getbApcoEtc() {
		return bApcoEtc;
	}

	/**
	 * @param bApcoEtc the bApcoEtc to set
	 */
	public void setbApcoEtc(String bApcoEtc) {
		this.bApcoEtc = bApcoEtc;
	}

	@Override
	public String toString() {
		return "Calendar [cal_id=" + cal_id + ", cal_title=" + cal_title + ", cal_cont=" + cal_cont + ", st_dt=" + st_dt
				+ ", end_dt=" + end_dt + ", allDay=" + allDay + ", depart_id=" + depart_id + ", emp_id=" + emp_id
				+ ", category_id=" + category_id + "]";
	}
	
}
