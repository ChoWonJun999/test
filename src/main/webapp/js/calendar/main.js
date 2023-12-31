var calendar;

$(function() {
	// sessionStorage에서 새로고침 여부를 확인합니다.
    if (!sessionStorage.getItem('reloaded')) {
        // 새로고침 표시를 설정합니다.
        sessionStorage.setItem('reloaded', 'true');
        
        // 페이지를 새로고침합니다.
        location.reload();
    }
	
	
	var calendarEl = document.getElementById('calendar');
    calendar = new FullCalendar.Calendar(calendarEl, {
    	plugins : [ "dayGrid", "timeGrid", "list", "interaction" ],
		header : {
			left : "prev,next today",
			center : "title", 
			right : "dayGridMonth,timeGridWeek,timeGridDay,listWeek"
		},
		locale: 'ko',
		height: "auto",
		contentHeight: 800,
		navLinks : true, // can click day/week names to navigate views
		selectable: true, // interaction 플러그인 필요, 사용자가 선택 및 드래그 하여 날짜를 선택할 수 있다.
		editable : false,
		eventLimit : true, // allow "more" link when too many events
		eventSources: [{ // 이벤트(일정) 추가
			events: function(info, successCallback, failureCallback) {
				// 1. fc-center 요소의 텍스트를 가져옵니다.
			    var text = $('.fc-center h2').text();
			    var formattedDate = "";
			    
			    if (!text) {
			        // 현재 날짜 가져오기
			        var currentDate = new Date();

			        // 현재 연도와 월 가져오기
			        var year = currentDate.getFullYear();
			        var month = currentDate.getMonth() + 1; // getMonth()는 0부터 시작하므로 1을 더해줍니다.

			        // 월이 한 자리 수이면 앞에 0을 붙여줍니다.
			        if (month < 10) {
			            month = '0' + month;
			        }

			        // yyyy-mm-01 형식의 문자열 생성
			        formattedDate = year + '-' + month + '-01';
			    } else {
			    	// 2. 텍스트를 Date 객체로 변환합니다.
				    var date = moment(text, 'YYYY년 M월').toDate();
				    
				    // 3. 날짜를 yyyy-mm-01 형식의 문자열로 포맷합니다.
				    formattedDate = moment(date).format('YYYY-MM-01');
			    }
			    
			    console.log(formattedDate);
				
				$.ajax({
			          url: cp + "/getAllCrawlingCalendarList",
			          type: "GET",
			          dataType: "json",
			          data: {
			        	bApcoCheckInDate: formattedDate
			          },
			          success: function(data) {
			            var fixedData = data.calendarList.map(function(array) {
			              return array;
			            })
			            successCallback(fixedData);
			          },
			          error: failureCallback
			        });
			}
		}],
		
		eventRender: function(info) { // 일정 hover
			var event = info.event;
			var element = info.el;
			var view = info.view;
			var startTimeElement = $(info.el).find('.fc-time');
			var payment = parseInt(event.extendedProps.bApcoPayment).toLocaleString();
			
			startTimeElement.text("#"+event.extendedProps.bApcoRoom + " (" + event.extendedProps.bApcoPerson + ")");
			$(element).popover({
				title: $('<div />', {
					'class': 'popoverTitleCalendar',
					'text': event.bApcoChannel
				}).css({
					'background': event.backgroundColor,
					'color': event.textColor
				}),
				content: $('<div />', {
					'class': 'popoverInfoCalendar'
		        }).append('<p><strong>카테고리:</strong> ' + event.extendedProps.category_nm + '</p>')
		          .append('<p><strong>상태:</strong> ' + event.extendedProps.bApcoStatus + '</p>')
		          .append('<p><strong>객실:</strong> ' + "#"+event.extendedProps.bApcoRoom + '</p>')
		          .append('<p><strong>예약자:</strong> ' + event.extendedProps.bApcoName + '</p>')
		          .append('<p><strong>핸드폰:</strong> ' + event.extendedProps.bApcoHp + '</p>')
		          .append('<p><strong>가격:</strong> ' + "￦"+payment + '</p>')
		          .append('<p><strong>시간:</strong> ' + getDisplayDate(event) + '</p>')
		          .append(getDisplayContent(event)),
		          delay: {
					show: "300",
					hide: "50"
		      	},
		      	trigger: 'hover',
		      	placement: 'top',
		      	html: true,
		      	container: 'body'
			});
			return filtering(event);
		},
		select: function(info) { // 날짜를 클릭 or 드래그 했을 때
			selectDate(info); // ==> calendar.js
		},
		eventTimeFormat: { // 달력에 표시될 time format
			hour: '2-digit',
			minute: '2-digit',
			hour12: false
		},
		eventClick: function(info) { // 이벤트 클릭
			selectEvent(info);
		},
		eventDrop : function(info) {
			resizeEvent(info);
		},
		eventResize : function(info) {
			resizeEvent(info)
		}
    });
    calendar.render();
    
    // 'prev', 'next', 'today' 버튼 클릭 이벤트 핸들러 추가
    document.querySelector('.fc-prev-button').addEventListener('click', function() {
      calendar.refetchEvents();
    });
    document.querySelector('.fc-next-button').addEventListener('click', function() {
      calendar.refetchEvents();
    });
    document.querySelector('.fc-today-button').addEventListener('click', function() {
      calendar.refetchEvents();
    });
   
	// 개인 일정 카테고리 불러오기
	$.getJSON(cp + "/getEmpCategoryList").done(function(data) {
		getDisplayCategory(data.categoryList, $("#empCategory"), "emp");
	});
	
	// 객실 카테고리
	$.getJSON(cp + "/getRoomList").done(function(data) {
		getDisplayRoom(data.roomList, $("#depCategory"), "room");
	});
	
	/*// 카테고리 체크박스 클릭
	$(document).delegate(".categoryList", "click", function() {
		var iTag = $(this).children("i");
		
		if(iTag.hasClass("on")) { // 체크되어 있을 때
			$(this).children("i").removeClass("fa-check-square-o on");
			$(this).children("i").addClass("fa-square-o off");	
		} else if(iTag.hasClass("off")) { // 체크되지 않았을 때
			$(this).children("i").removeClass("fa-square-o off");
			$(this).children("i").addClass("fa-check-square-o on");
		}
		
		calendar.rerenderEvents();
	});*/
	
	
	// 카테고리 체크박스 클릭 이벤트 핸들러
	$(document).delegate(".categoryList", "click", function() {
	    toggleCheckboxState(this, "category");
	    calendar.refetchEvents(); // 필터링 적용을 위해 이벤트 다시 불러오기
	});

	// 객실 체크박스 클릭 이벤트 핸들러
	$(document).delegate(".roomList", "click", function() {
	    toggleCheckboxState(this, "room");
	    calendar.refetchEvents(); // 필터링 적용을 위해 이벤트 다시 불러오기
	});
	
	
	// 카테고리 체크박스 전체 선택
	$(".categoryAll").on("click", function() {
		var selectedId = $(this).attr("id")
		
		if(selectedId === "empCategoryOn") {			// 개인 일정 전체 선택
			$("#empCategory div.categoryList > i").addClass("fa-check-square-o on");
			$("#empCategory div.categoryList > i").removeClass("fa-square-o off");
		} else if(selectedId  === "empCategoryOff") {	// 개인 일정 전체 해제
			$("#empCategory div.categoryList > i").addClass("fa-square-o off");
			$("#empCategory div.categoryList > i").removeClass("fa-check-square-o on");
		} else if(selectedId  === "depCategoryOn") {		// 부서 일정 전체 선택
			$("#depCategory div.roomList > i").addClass("fa-check-square-o on2");
			$("#depCategory div.roomList > i").removeClass("fa-square-o off2");
		} else if(selectedId  === "depCategoryOff") {	// 부서 일정 전체 해제
			$("#depCategory div.roomList > i").addClass("fa-square-o off2");
			$("#depCategory div.roomList > i").removeClass("fa-check-square-o on2");
		} 
		
		calendar.rerenderEvents();
	});
	
});


//체크박스 상태 토글 함수
function toggleCheckboxState(element, type) {
	console.log(element);
    var iTag = $(element).children("i");
    if(type === "category") {
        iTag.toggleClass("fa-check-square-o on fa-square-o off");
    } else if(type === "room") {
        iTag.toggleClass("fa-check-square-o on2 fa-square-o off2");
    }
}

function getDisplayCategory(data, loc, type) {
	var res = "";
	
	// 카테고리 리스트 출력
	$.each(data, function(index, entry) {
		res += "<hr>";
		res += "<div id=" + entry.category_id + " class='categoryList'>"
		res += "<i class='fa fa-lg fa-check-square-o on' style='color: " + entry.color + "; width: 20px;'></i>";
		res += "<span> " + entry.category_nm + "</span>";
		res += "<button class='btnCateModify btnCateUpdate'>"
		res += "<i class='fa fa-lg fa-wrench cateModify'></i>";
		res += "</button>";
		res += "<button class='btnCateModify btnCateDelete'>"
		res += "<i class='fa fa-lg fa-times'></i>";
		res += "</button>";
		res += "</div>";
	});
	
	// 카테고리 추가 버튼 출력
	/*if(type === "emp") {
		res += "<hr>";
		res += "<span id='addEmpCategory'>";
		res += "<i class='fa fa-lg fa-plus-circle' style='width: 20px;'></i>";
		res += "<span data-toggle='modal' data-target='#categoryModal'> 새로운 카테고리</span>";
	} else if(type === "dep") {
		if(emp_posi_id === "posi0001" || emp_posi_id === "posi0002" || emp_posi_id === "posi0003") {
			res += "<hr>";
			res += "<span id='addDepCategory'>";
			res += "<i class='fa fa-lg fa-plus-circle' style='width: 20px;'></i>";
			res += "<span data-toggle='modal' data-target='#categoryModal'> 새로운 카테고리</span>";
		}
	} */
	
	// 위에서 저장한 HTML태그를 document에 출력
	loc.append(res);
}

function getDisplayRoom(data, loc, type) {
	var res = "";
	
	// 카테고리 리스트 출력
	$.each(data, function(index, entry) {
		res += "<hr>";
		res += "<div id=" + entry.room_id + " class='roomList'>"
		res += "<i class='fa fa-lg fa-check-square-o on2' style='width: 20px;'></i>";
		res += "<span> " + entry.roomNm + "</span>";
		res += "<button class='btnCateModify btnCateUpdate'>"
		res += "<i class='fa fa-lg fa-wrench cateModify'></i>";
		res += "</button>";
		res += "<button class='btnCateModify btnCateDelete'>"
		res += "<i class='fa fa-lg fa-times'></i>";
		res += "</button>";
		res += "</div>";
	});
	
	// 위에서 저장한 HTML태그를 document에 출력
	loc.append(res);
}


// 받아온 시간을 moment.js를 이용하여 포맷에 정의된 형태로 반환
function getDisplayDate(event) {

	var displayDate; // 반환 될 데이터가 저장됨
	var startDate = moment(event.start); // start moment객체
	var endDate; // end moment객체

	if(event.end === null) { // startDate와 endDate가 같으면 event.end에 null이 들어감
		endDate = startDate;
	} else { 
		endDate = moment(event.end);
	}
	
	// allDay가 true일 때 하루를 빼줘야 달력에 정상 출력됨
	if(event.allDay == true) endDate.subtract(1, 'days');
	
	if(event.allDay === true) { // allDay === true
		if(startDate.format("YYYYMMDD") === endDate.format("YYYYMMDD")) // 하루일 때
			displayDate = startDate.format("YYYY-MM-DD");
		else // 이틀 이상일 때 
			displayDate = startDate.format("YYYY-MM-DD") + " ~ " + endDate.format("YYYY-MM-DD");
	} else { // allDay === false
		if(startDate.format("YYYYMMDDHHmm") === endDate.format("YYYYMMDDHHmm")) // 하루 안에서 같은 시간일 때(start = end)
			displayDate = startDate.format("YYYY-MM-DD");
		else if(startDate.format("YYYYMMDDHHmm") === endDate.format("YYYYMMDDHHmm")) // 하루 안에서 다른 시간일 때
			displayDate = startDate.format("YYYY-MM-DD") + " ~ " + endDate.format("YYYY-MM-DD");
		else // 이틀 이상 범위일 때
			displayDate = startDate.format("YYYY-MM-DD") + " ~ " + endDate.format("YYYY-MM-DD");
	}

	return displayDate;
}

function getDisplayContent(event) {
	var content = event.extendedProps.description;
	
	if(content != null) {
		return '<div class="popoverDescCalendar"><strong>설명:</strong> ' + content + '</div>';
	} else {
		return '';
	}
}

// 카테고리 체크박스 해제 시 해당 일정이 출력되지 않도록 해주는 함수
/*function filtering(event) {
	// 선택되어 있는 필터의 카테고리아이디들을 맵객체로 저장
	var checkedCategory = $(".on").map(function () {
		return $(this).parent().attr("id");
	}).get();
	
	// 맵객체에서 이벤트의 카테고리아이디가 일치하는지를 반환
	return checkedCategory.indexOf(event.extendedProps.category_id) >= 0;
}*/

//필터링 함수
function filtering(event) {
    var checkedCategories = $(".categoryList .on").map(function() {
        return $(this).parent().attr("id");
    }).get();

    var checkedRooms = $(".roomList .on2").map(function() {
        return $(this).parent().attr("id");
    }).get();

    /*var categoryMatch = checkedCategories.length === 0 || checkedCategories.indexOf(event.extendedProps.category_id) >= 0;
    var roomMatch = checkedRooms.length === 0 || checkedRooms.indexOf(event.extendedProps.bApcoRoom) >= 0;*/
    var categoryMatch = checkedCategories.indexOf(event.extendedProps.category_id) >= 0;
    var roomMatch = checkedRooms.indexOf(event.extendedProps.bApcoRoom) >= 0;

    return categoryMatch && roomMatch;
}


// 카테고리 추가, 수정 시 색상을 초기화 하기 위한 함수
function selectColor(color) {
	$(".btn-colorselector").css("backgroundColor", color);
	$(".dropdown-caret > li > a").removeClass("selected");
	$(".dropdown-caret > li > a").each(function(index, item) {
		if(item.title === color) {
			$(item).addClass("selected");
		}
	});
}

// rgb를 hex로 변경하는 함수
function rgb2hex(rgb) {
     if (rgb.search("rgb") == -1) {
          return rgb;
     } else {
          rgb = rgb.match(/^rgba?\((\d+),\s*(\d+),\s*(\d+)(?:,\s*(\d+))?\)$/);
          function hex(x) {
               return ("0" + parseInt(x).toString(16)).slice(-2);
          }
          return "#" + hex(rgb[1]) + hex(rgb[2]) + hex(rgb[3]); 
     }
}


function formatInput(inputElement) {
    var numbersOnly = inputElement.value.replace(/[^0-9]/g, '');
    inputElement.value = addCommas(numbersOnly);
}

function addCommas(number) {
    return number.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}


function uncomma(str) {
    return str.replace(/,/g, '');
}

function settingDate(obj){
	
	console.log(obj);
	
}

// 컬러셀렉터 표시
$("#colorselector").colorselector();

var picker = tui.DatePicker.createRangePicker({
    language: 'ko',
    startpicker: {
        date: new Date(),
        input: '#startpicker-input',
        container: '#startpicker-container'
    },
    endpicker: {
        date: new Date(),
        input: '#endpicker-input',
        container: '#endpicker-container'
    },
    type: 'date',
    format: 'yyyy-MM-dd'
});


picker._startpicker.on('afterSelect', function() {
    var startDate = picker.getStartDate();
    if (startDate) {
        var endDate = new Date(startDate);
        endDate.setDate(startDate.getDate() + 1);
        picker.setEndDate(endDate);
    }
});





