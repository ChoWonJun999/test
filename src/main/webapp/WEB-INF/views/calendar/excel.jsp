<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script src="${cp }/js/jquery-3.4.1.min.js"></script>

<!-- tui-date-picker css -->
<link href= "${cp }/plugin/tui-date-picker/tui-date-picker.css" rel="stylesheet"/>
<link href= "${cp }/plugin/tui-date-picker/tui-time-picker.css" rel="stylesheet"/>

<style>
  .table-hover tbody tr:hover {
    background-color: transparent !important;
  }
</style>

<script>
var cp = "${cp}";

//사용자 정보 클릭시 이벤트 핸들러
$(function(){
	
	function reloadData() {
		var startDate = $('#startDate').val(); // 'YYYY-MM-DD' 형식
        var endDate = $('#endDate').val();     // 'YYYY-MM-DD' 형식
		
		$.getJSON(cp + "/getAllExcelList", {startDate: startDate, endDate: endDate})
        .done(function(data) {
            mapDataToTable(data.excelList, startDate, endDate);
        });
	}
	
	 // 초기 로딩시 데이터 불러오기
    reloadData();

    // 날짜 변경시 데이터를 다시 불러오는 이벤트 핸들러
    $('#startDate, #endDate').on('change', reloadData);

});


function mapDataToTable(data, startDate, endDate) {
    var rooms = ['#2', '#3', '#4', '#5'];
    var dateRange = getDateRange(startDate, endDate);
    
    var tbodyContent = '';
    
    $.each(dateRange, function(dIndex, date) {
        $.each(rooms, function(rIndex, room) {
            var reservation = data.find(function(entry) {
                return entry.bApcoRoom === room && entry.bApcoCheckInDateMonth === date;
            });
            
            var row = '<tr>';
            if (rIndex === 0) { // 첫 번째 객실일 경우에만 날짜 표시
                row += '<td rowspan="4">' + date + '</td>';
            }
            
            if (reservation) {
                row += '<td>' + room + '</td>';
                row += '<td>' + reservation.bApcoChannel + '</td>';
                row += '<td>' + reservation.bApcoName + '</td>';
                row += '<td>' + reservation.bApcoPerson + '</td>';
                row += '<td>' + reservation.barbecueStatus + '</td>';
                row += '<td>' + reservation.bApcoPayment + '</td>';
                row += '<td>' + reservation.bApcoHp + '</td>';
                row += '<td>' + reservation.bApcoBooDate + '</td>';
                row += '<td>' + reservation.bApcoBooTime + '</td>';
                row += '<td>' + reservation.bApcoEtc + '</td>';
            } else { // 예약 정보가 없는 경우
                row += '<td>' + room + '</td>';
                row += '<td colspan="9">공실</td>';
            }
            
            row += '</tr>';
            tbodyContent += row;
        });
    });
    
    $('#excelList').html(tbodyContent);
}

function getDateRange(startDate, endDate) {
    var start = moment(startDate);
    var end = moment(endDate);
    var range = [];

    while (start <= end) {
        range.push(start.format('MM월 DD일'));
        start = start.add(1, 'days');
    }

    return range;
}


</script>
<div class="container-fluid">
	<div class="row">
		<div class="col-12">
			<div class="card">
				<div class="card-body">
					<div>
					</div>
					<div class="header-content clearfix">
							<div class="form-control tui-datepicker-input tui-datetime-input tui-has-focus">
								<input id="startDate" type="text" aria-label="Date" readonly="readonly">
								<span class="tui-ico-date"></span>
								<div id="startpicker-container" style="margin-left: -1px;"></div>
							</div>
							
							<div class="form-control tui-datepicker-input tui-datetime-input tui-has-focus">
								<input id="endDate" type="text" aria-label="Date" readonly="readonly">
								<span class="tui-ico-date"></span>
								<div id="startpicker-container" style="margin-left: -1px;"></div>
							</div>
						
						 <button type="button" class="btn mb-1 btn-success" id="btnInsertCalendar" style="float: right;">엑셀 다운로드</button>
						
					</div>
					<div class="table-responsive">
						<div class="form-group">
							<table class="table table-hover departTable">
							 <div>
								 	<span id="subtitle">
								 	
								 	</span>
									<thead>
										<tr>
											<th>예약채널</th>
											<th>구분</th>
											<th>날짜</th>
											<th>이름</th>
											<th>인원</th>
											<th>바베큐</th>
											<th>예약금액</th>
											<th>전화번호</th>
											<th>예약 시점(일자)</th>
											<th>예약 시점(시간)</th>
											<th>비고</th>
										</tr>
									</thead>
									<tbody id="excelList">
										<%-- <c:forEach items="${employeeList }" var="employee">
											<c:if test="${employee.EMP_ID != 'admin' }">
												<tr>
													<td>${employee.EMP_ID }</td>
													<td>${employee.EMP_NM }</td>
													<td>${employee.DEPART_NM }</td>
													<td>${employee.POSI_NM }</td>
													<c:choose>
														<c:when test="${employee.JOB_NM eq undefined}">
															<td>(직책없음)</td>
														</c:when>
														<c:otherwise>
															<td>${employee.JOB_NM}</td>
														</c:otherwise>
													</c:choose>
													<td><fmt:formatDate value="${employee.JOIN_DT }" pattern="yyyy-MM-dd"/></td>
													<td><button type="button" class="btn btn-info detailEmp" data-id="${employee.EMP_ID }" data-toggle="modal" data-target=".bd-example-modal-lg">사원 상세보기</button></td>
												</tr>
											</c:if>
										</c:forEach> --%>
									</tbody>
								</div>
							</table>
						</div>
					</div>
					
					
					<%-- <div id="page" class="bootstrap-pagination">
						<nav>
							<ul class="pagination justify-content-center">
								<!-- 이전 페이지 가기 : 지금 있는 페이지에서 한페이지 전으로 -->

								<c:choose>
									<c:when test="${map.page == 1 }">
										<li class="page-item disabled"><a class="page-link"
											href="#" tabindex="-1">&laquo;</a></li>
										<span aria-hidden="Previous"></span>
									</c:when>
									<c:otherwise>
										<li class="page-item"><a class="page-link"
											href="${cp }/employeeSearch?page=1"
											aria-label="Previous"> <span aria-hidden="true">&laquo;</span>
										</a></li>
									</c:otherwise>
								</c:choose>


								<c:choose>
									<c:when test="${map.page == 1 }">
										<li class="page-item disabled"><a class="page-link"
											href="#" tabindex="-1">&lt;</a></li>
									</c:when>
									<c:otherwise>
										<li class="page-item"><a class="page-link"
											href="${cp }/employeeSearch?page=${map.page-1}"
											tabindex="-1">&lt;</a></li>
									</c:otherwise>
								</c:choose>

								<c:forEach begin="1" end="${paginationSize }" var="i">
									<c:choose>
										<c:when test="${i == map.page }">
											<li class="page-item disabled"><span class="page-link">${i }</span></li>
										</c:when>
										<c:otherwise>
											<li class="page-item"><a class="page-link"
												href="${cp }/employeeSearch?page=${i }">${i }</a></li>
										</c:otherwise>
									</c:choose>
								</c:forEach>

								<c:choose>
									<c:when test="${map.page == paginationSize || paginationSize == 0}">
										<li class="page-item disabled"><a class="page-link"
											href="#">&gt;</a></li>
									</c:when>
									<c:otherwise>
										<li class="page-item"><a class="page-link"
											href="${cp }/employeeSearch?page=${page+1}">&gt;</a>
										</li>
									</c:otherwise>
								</c:choose>

								<c:choose>
									<c:when test="${map.page == paginationSize || paginationSize == 0}">
										<li class="page-item disabled"><a class="page-link"
											href="#">&raquo;</a></li>
										<span aria-hidden="Next"></span>
									</c:when>
									<c:otherwise>
										<li class="page-item"><a class="page-link"
											href="${cp }/employeeSearch?page=${paginationSize }"
											aria-label="Previous"> <span aria-hidden="true">&raquo;</span>
										</a></li>
									</c:otherwise>
								</c:choose>
							</ul>
						</nav>
					</div> --%>
				</div>
			</div>
		</div>
	</div>
</div>


<script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.29.1/moment.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/pikaday/1.8.0/pikaday.min.js"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/pikaday/1.8.0/css/pikaday.min.css">

<script>

	document.addEventListener('DOMContentLoaded', function() {
	  var startDateInput = document.getElementById('startDate');
	  var endDateInput = document.getElementById('endDate');

	  var startDatePicker = new Pikaday({
	    field: startDateInput,
	    format: 'YYYY-MM-DD',
	    onSelect: function(date) {
	      var endDate = moment(date).add(6, 'days').toDate();
	      endDatePicker.setMinDate(date);
	      endDatePicker.setDate(endDate, true);
	    }
	  });

	  var endDatePicker = new Pikaday({
	    field: endDateInput,
	    format: 'YYYY-MM-DD',
	    minDate: moment().toDate(),
	    setDefaultDate: moment().add(6, 'days').toDate(),
	    defaultDate: moment().add(6, 'days').toDate()
	  });

	// 초기 날짜 설정
	  startDatePicker.setDate(moment().toDate());
	  endDatePicker.setDate(moment().add(6, 'days').toDate());

	});

</script>

