# 문서 보러 가기 ➡️ [API 문서 링크](https://tdd-study-though-young-man.github.io/board-test-homework/src/main/resources/static/docs)

# API Documentation

# 1️⃣ API Specification

| 구분 | 화면 | 필요권한 | Method | Description | 요청 URL | Request JSON |
| --- | --- | --- | --- | --- | --- | --- |
| 관리 및 공통 | 로그인 | 관리자 | POST | 관리자 페이지 로그인 | `/api/admin/login` | `{"id": "{id}", "password": "{password}"}` |
|  |  | 사용자 | POST | 사용자 게시판 서비스 로그인 | `/api/user/login` | `{"id": "{id}", "password": "{password}"}` |
|  | 유저 관리 | 관리자 | GET | 전체 사용자 목록 조회 | `/api/admin/users` | `{}` |
|  |  | 관리자 | POST | 사용자 생성 | `/api/admin/users` | `{"id": "{id}", "password": "{password}", "name": "{name}"}` |
|  |  | 관리자 | PATCH | 사용자 정보 수정 | `/api/admin/users/{userId}` | `{"id": "{id}", "password": "{password}", "name": "{name}"}` |
|  |  | 관리자 | DELETE | 사용자 삭제 | `/api/admin/users/{userId}` | `{}` |
|  |  | 관리자 | GET | 사용자 비밀번호 초기화 | `/api/admin/users/{userId}/password` | `{}` |
|  |  | 관리자 | POST | 사용자 권한 부여 | `/api/admin/users/{userId}/roles` | `{"grantRoleList": [{"role": "ADMIN"}, {"role": "USER"}]}` |
| 검색 기능  | 게시글 검색   | 사용자   | GET    | 사용자가 게시판 내에서 특정 조건을 기준으로 게시글을 검색          | `/posts/search`           | `{"searchType": "string", "searchKeyword": "string"}`                                                                                                            |
| 카테고리 관리 | 카테고리 추가 | 관리자   | POST   | 관리자가 새로운 카테고리를 생성                                   | `/categories`             | `{"name": "String", "description": "String", "parentId": "int"}`                                                                                                 |
|  | 카테고리 조회 | 관리자   | GET    | 관리자가 모든 카테고리를 조회                                     | `/categories`             | 없음                                                                                                                                                             |
|  | 카테고리 삭제 | 관리자   | DELETE | 관리자가 특정 카테고리를 삭제                                     | `/categories/{categoryId}`| 없음                                                                                                                                                             |
|  | 카테고리 수정 | 관리자   | PUT    | 사용자가 카테고리의 이름, 설명, 부모 카테고리를 수정               | `/categories/{categoryId}`| `{"name": "String", "description": "String", "parentId": "int"}`                                                                                                 |
# 2️⃣ Authorization Rule (인증, 인가 방법)
1. 사용자는 "MEMBER", 관리자는 "ADMIN" 권한이 있어야 API 접근이 가능합니다.
2. HttpHeader에 { "Authorization" : "권한" }을 key-value로 보내주면 권한을 체크 후 API 접근이 가능합니다.
3. 권한을 주지 않고 헤더 없이 보낼 경우, { "Authorization" : "NONE" } 으로 처리됩니다.

```java
mockMvc.perform(get("/api/admin/users")
                .contentType(
                        MediaType.APPLICATION_JSON)
                        // header 지정하여 사용 -> ADMIN, MEMBER와 같은 일반 문자열 사용
                        .header("Authorization", "ADMIN")
                ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].id").value("mb1"))
                .andExpect(jsonPath("$.data[0].name").value("회원1"))
                .andExpect(jsonPath("$.statusCode").value(200))
                .andExpect(jsonPath("$.message").value("OK"));
```

# 3️⃣ 요구사항

## **관리 및 공통** (담당 : [DevSeoRex](https://github.com/ch4570/) )

- `관리자`는 관리자 페이지에 `로그인` 할 수 있다 - **Method Return Type (String - default(”jwttoken”))**
    - 관리자 페이지 로그인시, `아이디`와 `비밀번호`로 회원 조회 후 관리자가 아닐경우 “관리자 권한이 없습니다. 관리자 페이지에 진입할 수 없습니다.” 에러 표시가 필요하다.
    - `아이디`와 `비밀번호`가 틀린 경우 “아이디 또는 비밀번호가 다릅니다.” 에러 표시가 필요하다.
    - 회원 조회시 없는 회원인 경우 “존재하지 않는 회원입니다.” 에러 표시가 필요하다.

- `사용자`는 게시판 서비스에 `로그인` 할 수 있다 - **Method Return Type (String)**
    - `아이디`와 `비밀번호`가 틀린 경우 “아이디 또는 비밀번호가 다릅니다.” 에러 표시가 필요하다.

- `관리자`는 회원 목록 페이지에서 전체 `사용자 목록`을 `확인`할 수 있다 - **Method Return Type (List<MemberDto>)**
    - 현재 `Presentation Layer`에서 권한 처리가 되어 있지 않으므로, **전체 사용자 목록만 조회되면 된다.**

- `관리자`는 회원 관리 페이지에서 `사용자 계정`을 생성할 수 있다 - **Method Return Type (Long - MemberId)**
    - `사용자`를 생성할 경우, 이미 존재하는 `ID`로 가입 시도를 하는 경우 “이미 가입된 ID 입니다.” 에러 표시가 필요하다.
    - 사용자를 생성할 때 비밀번호는 `Bcrypt 암호화 처리`가 필요하다.
    
- `관리자`는 회원 관리 페이지에서 `사용자 계정`을 `수정`할 수 있다 - **Method Return Type (void)**
    - `사용자`를 수정할 경우, 이미 존재하는 `ID`로 수정 시도를 하는 경우  `"중복된 ID로 정보 변경은 불가능합니다."` 에러 표시가 필요하다.
    - 수정에 성공한다면, `DB`에 `반영` 되어야 한다.

- `관리자`는 회원 관리 페이지에서 `사용자 계정`을 `삭제`할 수 있다 - **Method Return Type (void)**
    - `사용자`를 `삭제`할 경우, 없는 회원의 `PK`가 `Path Variable`로 들어오면 “존재하지 않는 회원은 삭제할 수 없습니다.” 에러 표시가 필요하다.
    - 사용자 삭제는 `Hard Delete`로 처리한다.
    - 삭제에 성공할 경우 `DB`에 `반영`되어야 한다.
    - 계정 삭제시, 테이블과 `User_Role` 테이블의 데이터도 **함께 삭제되어야 한다.**

- `관리자`는 회원 관리 페이지에서 사용자의 `패스워드`를 `초기화` 할 수 있다 - **Method Return Type (String -  초기화 된 비밀번호)**
    - `사용자`를 조회할 때 없는 사용자라면 “존재하지 않는 회원의 비밀번호는 초기화할 수 없습니다.” 에러 표시가 필요하다.
    - `초기화` 할 패스워드는 `UUID`를 생성하여 초기화하며, `암호화`해서 회원 정보를 업데이트 해야 한다.

- `관리자`는 사용자에게 다양한  `권한`을 부여할 수 있다 - **Method Return Type (void)**
    - `부여`하려는 `권한`이 존재하지 않거나, `권한`을 `부여`하려는 사용자가 없는 사용자라면 “유효하지 않은 권한 부여 시도는 허용되지 않습니다.”, “존재하지 않는 회원에게 권한을 부여할 수 없습니다.” 에러 메시지를 **각각 표시한다.**
    - `사용자`는 여러 권한을 동시에 가질 수 있으므로, `Role`을 `List`로 받아서 저장되어야 한다.
    - 권한은 `Role` 테이블에 저장되며 `User`와 `Role` 사이의 `Join Table`인 `User_Role` 테이블에 권한과 유저를 매핑해준다.


## 검색 기능 (담당 : [CoRaveler](https://github.com/xpmxf4))

1. **게시글 검색 기능**
  - 목적 : `사용자`가 게시판 내에서 `특정 조건을 기준으로 게시글을 검색`할 수 있다.
  - URL : `/posts/search`
  - Method : `GET`
  - Query Parameters
  - `searchType` : string, 검색 조건 (제목, 제목+내용, 작성자)
      - `@NotBlank(message = "검색 조건은 비워둘 수 없습니다.")`
      - `@Size(min = 2, max = 50, message = "검색 조건은 2자 이상 50자 이하이어야 합니다.")`
  - `searchKeyword` : string, 검색 키워드
      - `@NotBlank(message = "검색 키워드는 비워둘 수 없습니다.")`
      - `@Size(min = 2, max = 50, message = "검색 키워드는 2자 이상 50자 이하이어야 합니다.")`
  - Method Return Type (JSON)
    - 성공 시 응답 예시 : `{ "status" : 200, "posts" : [ { post1 }, { post2 }, { post3 }, … ] }`
    - 검색 조건에 맞는 게시글이 없는 경우 : `{ "status" : 204, "posts" : [ ] }`
    - 잘못된 요청 파라미터로 인한 에러 : `{ "status" : 400, "error" : “잘못된 요청 파라미터가 들어왔습니다” }`
  - Possible Errors
    - `400 Bad Request` : 잘못된 요청 파라미터로 인한 에러

## 카테고리 관리 기능 (담당 : [CoRaveler](https://github.com/xpmxf4))

1. **카테고리 추가**
  - 목적 : 관리자가 새로운 카테고리를 생성할 수 있다.
  - URL : `/categories`
  - Method : `POST`
  - Request Body
    - `name` : String, 카테고리 이름
      - `@NotBlank(message = "카테고리 이름은 비워둘 수 없습니다.")`
      - `@Size(min = 2, max = 50, message = "카테고리 이름은 2자 이상 50자 이하이어야 합니다.")`
    - `description` : String, 카테고리 설명
      - `@Size(max = 200, message = "카테고리 설명은 200자를 초과할 수 없습니다.")`
    - `parentId` : int, 부모 카테고리 id (선택 사항)
      - `@Min(value = 1, message = "부모 카테고리 ID는 1 이상이어야 합니다.")`

        → 주어진 부모 카테고리의 존재 여부는 Service 계층에서

  - Method Return Type (JSON)
    - 카테고리 생성 성공 시 : `{ “status” : 200, message : “카테고리가 성공적으로 생성되었습니다.” }`
    - 카테고리 생성 실패 : `{ “status” : 400, “error” : “제공된 데이터가 유효하지 않습니다.” }`
    - 관리가 권한 없이 접근 : `{ “status” : 403, “error” : “관리자 권한이 없습니다. 관리자 페이지에 진입할 수 없습니다.” }`
  - Possible Errors
    - `400 Bad Request` : 필수 필드 누락, 잘못된 데이터 포맷, 또는 검증 실패
    - `403 Forbidden` : 관리자 권한 없이 관리자 권한 접근
2. **카테고리 조회**
  - 목적 : `관리자`가 모든 `카테고리를 조회`할 수 있다
  - URL : `/categories`
  - Method : `GET`
  - Method Return Type
    - 카테고리 조회 성공 시 : `{ “categories” : [ { category1 }, { category4 }, { category3 }, … ] }`
    - 카테고리가 없는 경우 : `{ “categories” : [] }`
    - 관리가 권한 없이 접근 : `{ “status” : 403, “error” : “관리자 권한이 없습니다.” }`
  - Possible Errors
    - `403 Forbidden` : 관리자 권한 없이 관리자 권한 접근
3. **카테고리 삭제**
  - 목적 : 관리자가 특정 카테고리를 삭제할 수 있다.
  - URL : `/categories/{categoryId}`
  - Method : `DELETE`
  - Method Return Type (JSON)
    - 카테고리 삭제 성공시 : `{ “status” : 200, message : “카테고리가 성공적으로 삭제되었습니다.” }`
    - 지정한 카테고리가 존재하지 않는 경우 : `{ “status” : 404, message : “해당 카테고리가 존재하지 않습니다.” }`
    - 관리가 권한 없이 접근 : `{ “status” : 403, “error” : “관리자 권한이 없습니다. 관리자 페이지에 진입할 수 없습니다.” }`
    - 게시글이 존재하는 카테고리는 변경 삭제가 안되는 경우 : `{ “status” : 400, “error” : “게시글이 존재하는 카테고리는 변경/삭제가 불가능합니다” }`
  - Possible Errors
    - `400 Bad Request` : 게시글이 존재하는 카테고리를 삭제하는 경우
    - `403 Forbidden` : 관리자 권한 없이 관리자 권한 접근
    - `404 Not Found` : 미존재 카테고리 삭제하는 경우
4. **카테고리 수정**
  - 목적 : 사용자가 카테고리의 이름, 설명, 부모 카테고리를 수정한다.
  - URL : `/categories/{categoryId}`
  - Method : PUT
  - Requset Body
    - `name` : String, 카테고리 이름
      - `@NotBlank(message = "카테고리 이름은 비워둘 수 없습니다.")`
      - `@Size(min = 2, max = 50, message = "카테고리 이름은 2자 이상 50자 이하이어야 합니다.")`
    - `description` : String, 카테고리 설명
      - `@Size(max = 200, message = "카테고리 설명은 200자를 초과할 수 없습니다.")`
    - `parentId` : int, 부모 카테고리 id (선택 사항)
      - `@Min(value = 1, message = "부모 카테고리 ID는 1 이상이어야 합니다.")`

        → 주어진 부모 카테고리의 존재 여부는 Service 계층에서

  - Method Return Type (JSON)
  - Possible Errors
    - `400 Bad Request` : 게시글이 존재하는 카테고리를 수정하는 경우
    - `403 Forbidden` : 관리자 권한 없이 관리자 권한 접근
    - `404 Not Found` : 미존재 카테고리 수정하는 경우
