package com.likelion.yourside.util.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Chapter 2. CustomAPIResponse
 * 사용자의 요청에 대한 공통적인 '형식'을 만들기 위해 CustomAPIResponse객체를 작성한다.
 * 우리의 웹 서버는 공통적으로 아래와 같은 구조를 반환하길 원한다.
 * {
 *     "status" : 200, // Http 프로토콜 통신 결과를 나타내는 상태코드, 이 코드를 보고 요청의 결과를 추측할 수 있다.
 *     "data" : { // 클라이언트가 요청한 실제 데이터, Frontend에서 이 데이터를 가공하여 사용자에게 보여준다.
 *         "email" : user@test.com,
 *         "password" : 1234
 *     }
 *     "message" : "조회에 성공하였습니다." // Frontend 단에서 api 통신에 대한 결과를 확인하기 쉽도록 메시지를 반환해준다.
 * }
 * 위와 같은 리턴 형식을 맞추기 위해 멤버변수로 status,data,message가 필요하다.
 * 클라이언트가 요청한 데이터는 "data"에 담기게 되는데, 아직 어떤 데이터가 반환될지 결정되지 않았으므로 Generic형식(<T>)으로 작성 할 것이다.
 * */

//-lombok 라이브러리-
@AllArgsConstructor // 모든 멤버변수를 매개변수로 받아 할당하는 생성자를 자동으로 작성해준다.
@NoArgsConstructor // 기본생성자(어떠한 매개변수도 받지 않는 생성자)를 자동으로 작성해준다.
@Getter @Setter // 모든 멤버변수에 대해 Getter, Setter함수를 자동으로 작성해준다.
public class CustomAPIResponse<T>{

    // 공통적으로 반환되길 원하는 값들
    private int status;
    private T data; // 아직 어떤 형태로 값이 반환될 지 정해지지 않았으므로 Generic으로 작성한다.
    private String message;

    // 요청에 따라 CustomAPIResponse 객체의 값을 적절히 수정해야한다.

    // 통신이 성공할 경우에 대한 응답 값을 작성하는 함수
    // <T> 를 선언부에 작성한 이유는, 해당 함수의 data 값이 아직 정해지지 않았기 때문이다.
    // Generic을 사용하여 함수 호출 시점에 T의 타입을 우리가 결정할 수 있다.
    public static <T> CustomAPIResponse<T> createSuccess(int status, T data, String message) {
        return new CustomAPIResponse<>(status, data, message);
    }

    // 통신이 실패할 경우에 대한 응답 값을 작성하는 함수
    public static <T> CustomAPIResponse<T> createFailWithout(int status, String message) {
        return new CustomAPIResponse<>(status, null, message);
    }
}

