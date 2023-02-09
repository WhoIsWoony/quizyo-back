package com.whoiswoony.springtutorial.controller.exception

class CustomException(val errorCode: ErrorCode): RuntimeException()