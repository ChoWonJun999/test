$(function() {
	// 하루 종일 버튼을 클릭했을 때
	$("#allDay").on("click", changeAllDay);
	
	// 일정 INSERT
	$("#btnInsertCalendar").on(
			"click",
			function() {
				// 정규식 검사
				if ($("#bApcoName").val().trim() === "") { // 일정 제목
					Swal({
						title : '예약자를 입력하세요.',
						type : 'error',
						timer : 1500,
						showConfirmButton : false,
					});
					return null;
				}
				if ($("#roomList").val() === null) { // 카테고리
					Swal({
						title : '객실을 선택하세요.',
						type : 'error',
						timer : 1500,
						showConfirmButton : false,
					});
					return null;
				}
				if ($("#categoryList").val() === null) { // 카테고리
					Swal({
						title : '카테고리를 선택하세요.',
						type : 'error',
						timer : 1500,
						showConfirmButton : false,
					});
					return null;
				}
				if (picker._endpicker.getDate() === null) { // 종료시간
					Swal({
						title : '종료시간을 선택하세요.',
						type : 'error',
						timer : 1500,
						showConfirmButton : false,
					});
					return null;
				}

				$("#bApcoPerson").val(uncomma($("#bApcoPerson").val()));
				$("#bApcoPayment").val(uncomma($("#bApcoPayment").val()));

				var startDate = picker._startpicker.getDate();
				var endDate = picker._endpicker.getDate();

				// 날짜를 UTC 시간대로 변환
				var utcStartDate = new Date(Date.UTC(startDate.getFullYear(),
						startDate.getMonth(), startDate.getDate(), startDate
								.getHours(), startDate.getMinutes(), startDate
								.getSeconds()));
				var utcEndDate = new Date(Date.UTC(endDate.getFullYear(),
						endDate.getMonth(), endDate.getDate(), endDate
								.getHours(), endDate.getMinutes(), endDate
								.getSeconds()));

				$.ajax({
					url : cp + "/checkAvailability",
					type : "POST",
					data : $("#calendarForm").serialize() + "&start="
							+ utcStartDate.toISOString() + "&end="
							+ utcEndDate.toISOString(),
					success : function(data) {
						if (data.isAvailable) {
							$.ajax({
								url : cp + "/insertNewCalendar",
								type : "POST",
								data : $("#calendarForm").serialize()
										+ "&start="
										+ utcStartDate.toISOString() + "&end="
										+ utcEndDate.toISOString(),
							    beforeSend: function() {
								        // 로딩 모달 표시
								        $("#loadingModal").modal("show");
							    },
								success : function(data) {
									$("#calendarModal").modal("hide");
									Swal({
										title : '생성되었습니다.',
										type : 'success',
										timer : 1500,
										showConfirmButton : false,
									});

									// 캘린더 업데이트
									calendar.refetchEvents();
								},
							    error: function() {
							        // 에러 처리...
							    },
							    complete: function() {
							        // 로딩 모달 숨기기
							        $("#loadingModal").modal("hide");
							    }
							});
						} else {
							Swal({
								title : '이미 해당 날짜 객실에 예약이 되어있습니다.',
								type : 'error',
								timer : 1500,
								showConfirmButton : false,
							});
						}

					}
				});

			});

	// 일정 UPDATE
	$("#btnUpdateCalendar").on(
			"click",
			function() {
				// 정규식 검사
				// 정규식 검사
				if ($("#bApcoName").val().trim() === "") { // 일정 제목
					Swal({
						title : '예약자를 입력하세요.',
						type : 'error',
						timer : 1500,
						showConfirmButton : false,
					});
					return null;
				}
				if ($("#roomList").val() === null) { // 카테고리
					Swal({
						title : '객실을 선택하세요.',
						type : 'error',
						timer : 1500,
						showConfirmButton : false,
					});
					return null;
				}
				if ($("#categoryList").val() === null) { // 카테고리
					Swal({
						title : '카테고리를 선택하세요.',
						type : 'error',
						timer : 1500,
						showConfirmButton : false,
					});
					return null;
				}
				if (picker._endpicker.getDate() === null) { // 종료시간
					Swal({
						title : '종료시간을 선택하세요.',
						type : 'error',
						timer : 1500,
						showConfirmButton : false,
					});
					return null;
				}

				$("#bApcoPerson").val(uncomma($("#bApcoPerson").val()));
				$("#bApcoPayment").val(uncomma($("#bApcoPayment").val()));

				var startDate = picker._startpicker.getDate();
				var endDate = picker._endpicker.getDate();

				// 날짜를 UTC 시간대로 변환
				var utcStartDate = new Date(Date.UTC(startDate.getFullYear(),
						startDate.getMonth(), startDate.getDate(), startDate
								.getHours(), startDate.getMinutes(), startDate
								.getSeconds()));
				var utcEndDate = new Date(Date.UTC(endDate.getFullYear(),
						endDate.getMonth(), endDate.getDate(), endDate
								.getHours(), endDate.getMinutes(), endDate
								.getSeconds()));

				$.ajax({
					url : cp + "/checkAvailability",
					type : "POST",
					data : $("#calendarForm").serialize() + "&start="
							+ utcStartDate.toISOString() + "&end="
							+ utcEndDate.toISOString(),
					success : function(data) {
						if (data.isAvailable) {
							$.ajax({
								url : cp + "/updateNewCalendar",
								type : "POST",
								data : $("#calendarForm").serialize()
										+ "&start="
										+ utcStartDate.toISOString() + "&end="
										+ utcEndDate.toISOString(),
								beforeSend: function() {
							        // 로딩 모달 표시
							        $("#loadingModal").modal("show");
							    },
								success : function(data) {
									$("#calendarModal").modal("hide");

									Swal({
										title : '수정되었습니다.',
										type : 'success',
										timer : 1500,
										showConfirmButton : false,
									});

									// 캘린더 업데이트
									calendar.refetchEvents();
								},
							    error: function() {
							        // 에러 처리...
							    },
							    complete: function() {
							        // 로딩 모달 숨기기
							        $("#loadingModal").modal("hide");
							    }
							});
						} else {
							Swal({
								title : '이미 해당 날짜 객실에 예약이 되어있습니다.',
								type : 'error',
								timer : 1500,
								showConfirmButton : false,
							});
						}

					}
				});

			});

	// 일정 DELETE
	$("#btnDeleteCalendar").on(
			"click",
			function() {
				Swal(
						{
							title : '정말 삭제하시겠습니까?',
							html : "<span class='text-danger'>\"" + "#"
									+ $("#roomList").val() + " / "
									+ $("#bApcoName").val()
									+ "\"</span> 일정이 삭제됩니다.",
							type : 'warning',
							showCancelButton : true,
							focusConfirm : false,
							confirmButtonColor : '#3085d6',
							cancelButtonColor : '#d33',
							confirmButtonText : '네',
							cancelButtonText : '아니오'
						}).then(function(result) {
					if (result.value) {
						
						// disabled 풀기
						$("#roomList").prop("disabled", false);
						$("#categoryList").prop("disabled", false);
						
						var startDate = picker._startpicker.getDate();
						var endDate = picker._endpicker.getDate();

						// 날짜를 UTC 시간대로 변환
						var utcStartDate = new Date(Date.UTC(startDate.getFullYear(),
								startDate.getMonth(), startDate.getDate(), startDate
										.getHours(), startDate.getMinutes(), startDate
										.getSeconds()));
						var utcEndDate = new Date(Date.UTC(endDate.getFullYear(),
								endDate.getMonth(), endDate.getDate(), endDate
										.getHours(), endDate.getMinutes(), endDate
										.getSeconds()));
						
						
						$.ajax({
							url : cp + "/deleteNewCalendar",
							data : $("#calendarForm").serialize() + "&start="
									+ utcStartDate.toISOString() + "&end="
									+ utcEndDate.toISOString(),
							type : "post",
							beforeSend: function() {
						        // 로딩 모달 표시
						        $("#loadingModal").modal("show");
							},
							success : function(data) {
								$("#calendarModal").modal("hide");

								Swal({
									title : '삭제되었습니다.',
									type : 'success',
									timer : 1500,
									showConfirmButton : false,
								});

								calendar.refetchEvents();
							},
						    error: function() {
						        // 에러 처리...
						    },
						    complete: function() {
						        // 로딩 모달 숨기기
						        $("#loadingModal").modal("hide");
						    }
						});
					}
				});
			});
});

// 달력의 날짜를 선택했을 때 일정 추가 모달 출력
var selectDate = function(info) {
	// 모달 셋팅 변경
	$("#calendarModalTitle")
			.html(
					"<i class='fa fa-calendar-check-o fa-lg' style='color: black;'> </i> 일정 추가");

	// 일정 초기화
	$("#bApcoName").val("").prop('readOnly', false);
	$("#bApcoHp").val("").prop('readOnly', false);
	$("#bApcoPayment").val("").prop('readOnly', false);
	$("#bApcoEtc").val("").prop('readOnly', false);
	$("#bApcoPerson").val("").prop('readOnly', false);
	$("#gubun").val("").prop('readOnly', false);
	
	//disabled 설정 풀기
	$("#roomList").prop("disabled", false);
	$("#categoryList").prop("disabled", false);

	$("#bApcoKey").val("");

	// 날짜 초기화
	var startDate = moment(info.start).format("YYYY-MM-DD HH:mm"); // fullcalendar에서
																	// 선택한 시작 시간
	var endDate = moment(info.end).subtract(60, 'second').add(1, 'day').format(
			"YYYY-MM-DD HH:mm");// fullcalendar에서 선택한 종료 시간

	picker.setStartDate(new Date(startDate)); // date picker start date에 선택한
												// 시간을 입력
	picker.setEndDate(new Date(endDate)); // date picker end date에 선택한 시간을 입력

	// 하루 종일 초기화
	/*
	 * $("#allDay").prop("checked", false); changeAllDay();
	 */

	// room 초기화
	$("#roomList").empty();
	$.getJSON(cp + "/getRoomList")
			.done(
					function(datas) { // 개인 일정
						var res = "<option value='' selected disabled hidden>==선택하세요==</option>";
						$.each(datas.roomList, function(idx, data) {
							res += "<option value='" + data.room_id + "'>"
									+ data.roomNm + "</option>";
						});
						res += "</optgroup>";
						$("#roomList").append(res);
					});

	// 카테고리 초기화
	$("#categoryList").empty();
	$.getJSON(cp + "/getEmpCategoryList")
			.done(
					function(datas) { // 개인 일정
						var res = "<option value='' selected disabled hidden>==선택하세요==</option>";
						res += "<optgroup label='<예약 일정>'>";
						$.each(datas.categoryList, function(idx, data) {
							if (data.category_nm == "현금") {
								res += "<option value='" + data.category_nm
										+ "' selected>" + data.category_nm
										+ "</option>";
							}
						});
						res += "</optgroup>";
						$("#categoryList").append(res);
					});

	// 버튼 초기화
	$("#btnInsertCalendar").show();
	$("#btnDeleteCalendar").hide();
	$("#btnUpdateCalendar").hide();

	// 모달 실행
	$("#calendarModal").modal("show");
}

// 달력의 일정을 클릭했을 때 일정 수정 모달 출력
var selectEvent = function(info) {
	// 모달 셋팅 변경
	$("#calendarModalTitle")
			.html(
					"<i class='fa fa-calendar-check-o fa-lg' style='color: black;'> </i> 일정 상세");

	// 날짜 초기화
	var startDate = moment(info.event.start).format("YYYY-MM-DD HH:mm"); // fullcalendar에서
																			// 선택한
																			// 시작
																			// 시간
	// var endDate = moment(info.event.end).subtract(60,
	// 'second').format("YYYY-MM-DD HH:mm");// fullcalendar에서 선택한 종료 시간
	var endDate = moment(info.event.end).format("YYYY-MM-DD 00:00");

	picker.setStartDate(new Date(startDate)); // date picker start date에 선택한
												// 시간을 입력
	// picker.setEndDate(new Date(endDate)); // date picker end date에 선택한 시간을 입력
	picker.setEndDate(new Date(endDate))

	// 하루 종일 초기화
	/*
	 * $("#allDay").prop("checked", info.event.allDay); changeAllDay();
	 */

	$("#bApcoName").val(info.event.extendedProps.bApcoName).prop('readOnly', true);
	$("#bApcoHp").val(info.event.extendedProps.bApcoHp).prop('readOnly', true);
	$("#bApcoPayment").val(addCommas(info.event.extendedProps.bApcoPayment)).prop('readOnly', true);
	$("#bApcoEtc").val(info.event.extendedProps.bApcoEtc).prop('readOnly', true);
	$("#bApcoPerson").val(info.event.extendedProps.bApcoPerson).prop('readOnly', true);
	$("#gubun").val(info.event.extendedProps.gubun).prop('readOnly', true);

	$("#bApcoKey").val(info.event.extendedProps.bApcoKey);

	// room 초기화
	$("#roomList").empty();
	$.getJSON(cp + "/getRoomList")
			.done(
					function(datas) { // 개인 일정
						var res = "<option value='' selected disabled hidden>==선택하세요==</option>";
						$
								.each(
										datas.roomList,
										function(idx, data) {
											if (data.room_id === info.event.extendedProps.bApcoRoom)
												res += "<option selected ";
											else
												res += "<option ";
											res += "value='" + data.room_id
													+ "'>" + data.roomNm
													+ "</option>";
										});
						res += "</optgroup>";
						$("#roomList").append(res);
					});

	// 카테고리 초기화
	$("#categoryList").empty();
	$.getJSON(cp + "/getEmpCategoryList")
			.done(
					function(datas) { // 개인 일정
						var res = "<option value='' selected disabled hidden>==선택하세요==</option>";
						res += "<optgroup label='<예약 일정>'>";
						$
								.each(
										datas.categoryList,
										function(idx, data) {
											if (data.category_id === info.event.extendedProps.category_id)
												res += "<option selected ";
											else
												res += "<option ";
											res += "value='" + data.category_nm
													+ "'>" + data.category_nm
													+ "</option>";
										});
						res += "</optgroup>";
						$("#categoryList").append(res);
					});
	
	//disabled 설정
	$("#roomList").prop("disabled", true);
	$("#categoryList").prop("disabled", true);
	

	// 버튼 초기화
	$("#btnInsertCalendar").hide();
	$("#btnDeleteCalendar").show();
	$("#btnUpdateCalendar").hide();

	if (info.event.extendedProps.bApcoChannel != "현금") {
		$("#btnInsertCalendar").hide();
		$("#btnUpdateCalendar").hide();
		$("#btnDeleteCalendar").hide();
	}

	// 모달 실행
	$("#calendarModal").modal("show");
}

var resizeEvent = function(info) {
	var start = +info.event.start;
	var end;
	if (info.event.allDay === true)
		end = +(moment(info.event.end).subtract(60, 'second'));
	else
		end = +info.event.end;

	$.ajax({
		url : cp + "/updateCalendarSize",
		type : "POST",
		data : "cal_id=" + info.event.id + "&start=" + start + "&end=" + end,
		success : function() {
			Swal({
				title : '수정되었습니다.',
				type : 'success',
				timer : 1500,
				showConfirmButton : false,
			});
		}
	});
}

// 하루 종일 체크박스의 상태에 따라 데이트 피커 포맷 변경
var changeAllDay = function() {
	if ($("#allDay").prop("checked")) {
		// 시작날짜와 종료날짜에 시간:분을 제거
		picker._startpicker.setDateFormat("yyyy-MM-dd");
		picker._endpicker.setDateFormat("yyyy-MM-dd");
		// 타임피커 숨기기
		$(picker._startpicker._timePicker._container).hide();
		$(picker._endpicker._timePicker._container).hide();
	} else {
		// 시작날짜와 종료날짜에 시간:분을 추가
		picker._startpicker.setDateFormat("yyyy-MM-dd HH:mm");
		picker._endpicker.setDateFormat("yyyy-MM-dd HH:mm");
		// 타임피커 보이기
		$(picker._startpicker._timePicker._container).show();
		$(picker._endpicker._timePicker._container).show();
	}
}



