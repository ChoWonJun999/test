<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<script src="${cp }/js/jquery-3.4.1.min.js"></script>

<!-- tui-date-picker css -->
<link href="${cp }/plugin/tui-date-picker/tui-date-picker.css"
	rel="stylesheet" />
<link href="${cp }/plugin/tui-date-picker/tui-time-picker.css"
	rel="stylesheet" />
	


<style>
.table-hover tbody tr:hover {
	background-color: transparent !important;
}
</style>

<script>

	var cp = "${cp}";

	//사용자 정보 클릭시 이벤트 핸들러
	$(function() {

		function reloadData() {
			var startDate = $('#startDate').val(); // 'YYYY-MM-DD' 형식
			var endDate = $('#endDate').val(); // 'YYYY-MM-DD' 형식

			$.getJSON(cp + "/systemLogList").done(function(data) {
				mapDataToTable(data.logList);
			});
		}
		
		// Reload data at an interval for real-time effect
	    setInterval(reloadData, 5000); // Reload data every 5 seconds
		
		// 초기 로딩시 데이터 불러오기
		reloadData();

	});
	

	function mapDataToTable(data) {

        var tbodyContent = "";
		
		$.each(data, function(index, log) {
				console.log(log);
		        var row = '<tr>';
	            row += '<td>' + log.log_text + '</td>';
		        row += '</tr>';
		        
		        tbodyContent += row;
		});


		$('#logList').html(tbodyContent);
	}
	
</script>
<div class="container-fluid">
	<div class="row">
		<div class="col-12">
			<div class="card">
				<div class="card-body">
					<div></div>
					<div class="table-responsive">
						<H2>SYSTEM LOG [내역]</H2>
						<div class="form-group">
							<table id="logTable" class="table table-hover departTable">
								<div>
									<tbody id="logList">
									</tbody>
								</div>
							</table>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>


<script
	src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.29.1/moment.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/pikaday/1.8.0/pikaday.min.js"></script>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/pikaday/1.8.0/css/pikaday.min.css">

<script>
	document.addEventListener('DOMContentLoaded', function() {
		var startDateInput = document.getElementById('startDate');
		var endDateInput = document.getElementById('endDate');

		var koreanLanguage = {
			previousMonth : '이전 달',
			nextMonth : '다음 달',
			months : [ '1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월',
					'10월', '11월', '12월' ],
			weekdays : [ '일요일', '월요일', '화요일', '수요일', '목요일', '금요일', '토요일' ],
			weekdaysShort : [ '일', '월', '화', '수', '목', '금', '토' ]
		};

		var startDatePicker = new Pikaday({
			field : startDateInput,
			i18n: koreanLanguage,
			format : 'YYYY-MM-DD',
			onSelect : function(date) {
				var endDate = moment(date).add(6, 'days').toDate();
				endDatePicker.setMinDate(date);
				endDatePicker.setDate(endDate, true);
			}
		});

		var endDatePicker = new Pikaday({
			field : endDateInput,
			i18n: koreanLanguage,
			format : 'YYYY-MM-DD',
			minDate : moment().toDate(),
			setDefaultDate : moment().add(6, 'days').toDate(),
			defaultDate : moment().add(6, 'days').toDate()
		});

		// 초기 날짜 설정
		startDatePicker.setDate(moment().toDate());
		endDatePicker.setDate(moment().add(6, 'days').toDate());

	});
</script>

