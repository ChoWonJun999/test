<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="nk-sidebar">           
    <div class="nk-nav-scroll">
        <ul class="metismenu" id="menu">
            <li>
                <a href="${cp }/main" aria-expanded="false">
                    <i class="fa fa-calendar menu-icon"></i><span class="nav-text">일정 관리(달력)</span>
                </a>
            </li>
            <li>
                <a href="${cp }/excel" aria-expanded="false">
                    <i class="icon-grid menu-icon"></i><span class="nav-text">일정 관리(엑셀)</span>
                </a>
            </li>
        </ul>
    </div>
</div>