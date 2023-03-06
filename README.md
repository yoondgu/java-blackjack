# java-blackjack

블랙잭 미션 저장소

## 우아한테크코스 코드리뷰

- [온라인 코드 리뷰 과정](https://github.com/woowacourse/woowacourse-docs/blob/master/maincourse/README.md)

## 📝 미션 명세

### 🗄 도메인 구성 요소

- 카드 덱
    - 카드
        - 심볼
        - 문자 (2~10, Ace, King, Queen, Jack)
- 참가자
    - 딜러
    - 플레이어
        - 이름
        - 가지고 있는 카드
- 최종 승패

### 🚀 기능 목록

#### 도메인 기능

* 컨트롤러

- [x] 특정 플레이어가 히트/스테이가 가능하면 계속 묻는다.

* 카드

- [x] 카드를 생성한다.
- [x] 카드는 심볼(스페이드, 하트, 클로버, 다이아몬드)을 가진다.
- [x] 카드는 문자(2~10, Ace, King, Queen, Jack)을 가진다.
    - [x] 각 문자는 값을 가진다.


* 카드 덱

- [x] 카드 덱은 여러 장의 카드를 가진다.
- [x] 카드 덱에서 주어진 장수만큼 카드를 뽑는다.
    - [x]  `예외` 카드 덱에 더 이상 카드가 없다면 예외를 발생한다.

* 블랙잭 게임

- [x] 블랙잭 게임은 덱을 만든다.
- [x] 블랙잭 게임은 참가자를 가진다.
- [x] 게임 시작을 위해 카드를 나눠준다.
- [x] 플레이어의 보유 카드를 모두 확인한다.
- [x] 딜러의 보유 카드를 한 장만 확인한다.
- [x] 딜러의 보유 카드를 모두 확인한다.
- [x] 히트/스테이를 할 수 있는 모든 플레이어 이름을 반환한다.
- [x] 특정 플레이어에게 카드를 준다.
- [x] 딜러가 히트가 가능하면 계속 카드를 준다.
- [x] 모든 플레이어의 최종 카드 결과를 확인한다.
- [x] 모든 플레이어의 승패 결과를 확인한다.
    - 버스트인 플레이어는 무조건 패배이다.
    - 딜러가 버스트이면, 버스트가 아닌 플레이어는 무조건 승리이다.
    - 딜러가 버스트가 아니면, 버스트가 아닌 플레이어는 딜러와 카드 합을 비교해서 승패를 결정한다.

* 블랙잭 결과

- [x] 이름, 보유카드, 결과를 가진다.

* 블랙잭 게임 덱 생성기

- [x] 카드들을 만든다.
    - [x] `예외` 카드 덱은 중복을 허용하지 않는다.
    - [x] `예외` 카드 덱의 카드 장수는 52장이어야 한다.
- [x] 카드들을 섞는다.

* 참가자

- [x] 참가자는 카드를 한장씩 받을 수 있다.
- [x] 참가자가 가지고 있는 카드의 합을 계산한다.
    - 카드의 가 A이면 1 또는 11 중 계산한다.
    - King, Queen, Jack은 10으로 계산한다.
- [x] 참가자가 블랙잭/버스트(현재 가진 카드 합이 21 이상)인지 판단한다.

* 참가자들

- [x] 참가자들은 딜러와 1~n명의 플레이어로 이루어져 있다.
    - [x] `예외` 플레이어의 이름 간 중복이 허용하지 않는다.
    - [x] `예외` 플레이어의 이름과 딜러의 이름 간 중복이 허용되지 않는다.
- [x] 모든 참가자가 카드를 2장씩 갖도록 한다.
- [x] 참가자의 보유 카드를 모두 확인하되, 딜러 카드는 첫 카드만 확인한다.
- [x] 플레이어가 히트/스테이가 가능한지 확인한다.
- [x] 특정 이름을 가진 플레이어를 찾아 반환한다.


* 딜러

- [x] 딜러의 카드 합이 17 미만인지 판단한다.
- [x] 합을 비교해 플레이어의 승패를 결정한다.
    - 딜러가 특정 플레이어의 승패를 알려준다. (승리, 패배, 무승부)

* 플레이어

- [x] 플레이어는 이름을 가진다.
    - [x] `예외` 빈 문자열이거나 공백인 이름은 허용하지 않는다.

* 플레이어 승패 결과 목록

- [x] 각 플레이어의 이름에 따른 승패 결과를 저장한다.
- [x] 딜러의 승패 결과를 합산해서 반환한다.

#### UI 기능

- [x] 플레이어의 이름을 입력받는다.
    - [x] 이름을 쉼표 기준으로 분리한다.
- [x] 카드를 더 받을지 여부를 정해진 키워드로 입력받는다.
    - [x] `예외` 정해진 키워드가 아닌 경우 예외를 발생시킨다.
- [x] 카드를 나눠준 결과를 출력한다.
- [x] 플레이어가 가진 카드들을 출력한다.
- [x] 딜러가 카드를 받은 여부를 출력한다.
- [x] 최종 카드 합산 결과를 출력한다.
- [x] 최종 승패를 출력한다.

### ✅ 1단계 리뷰 반영 및 리팩터링 목록

#### refactor

- [x] Controller 실행 코드 메서드 분리
- [x] Participant에서 문자 합 계산하도록 수정
- [x] 블랙잭/버스트 여부 확인 메서드 호출 관계 수정
- [ ] 모호한 클래스명, 메서드명 수정
    - [x] GameResult -> FinalCards (최종 보유 카드, 계산된 문자 합)
    - [x] WinResult -> JudgeResult, PlayerWinResults -> PlayerJudgeResults
    - [x] Number -> Letter
    - [ ] 테스트 메서드명 컨벤션 통일 (대소문자, 언더바 등)
- [x] 딜러와 플레이어 정보 조회 및 출력 중복코드 통합
    - [x] Dealer 클래스에도 name 필드 추가
    - [ ] Participant 부모 클래스 추상클래스로 변경, 추상 메서드 활용
- [ ] 클래스 책임 검토, 필요에 따라 분리
    - [ ] Participants 클래스 책임 분리
        - [x] Players 일급 콜렉션 객체 분리
        - [ ] Dealer에서 첫번째 카드만 반환하는 메서드 직접 수행
        - [ ] 특정 이름의 참가자를 조회해서 BlackJackGame에서 로직을 수행하게 할지 / 내부에서 처리할지 결정
    - [ ] Cards 일급콜렉션 객체 분리
        - [ ] Cards 객체에서 카드의 문자 합 계산하도록 책임 이관
    - [ ] 히트/스테이 진행 여부 도메인에서 관리하도록 수정
    - [ ] FinalCards 생성 시 Participant 직접 참조하지 말고 Cards를 받기
    - [ ] 두 값을 비교해 승무패를 결정하는 클래스 분리
    - [ ] 모든 참가자에게 카드 나눠주는 메서드 위치 검토
- [ ] 에러메시지 클래스 분리
- [ ] 매직 넘버 상수화
    - [ ]  히트/스테이 여부 키워드
    - [x]  첫번째 카드 인덱스 값
- [ ] VO에 equals, hashCode 재정의하기
- [ ] final 키워드 적용
- [ ] OutputView 메서드 분리

#### feat, fix

- [ ] 참여자 이름에서 예약어(딜러) 검증 추가
- [ ] 플레이어가 버스트인 경우 패배 처리
- [ ] 카드 문자 합 계산 시 ACE가 여러 장일 경우 처리
- [ ] 잘못된 입력에 대한 오류 안내 및 재입력 요청 기능 구현
- [ ] 플레이어 인원 수 검증
- [ ] 그 외 고려하지 못한 예외 사항 검토

#### test

- [ ] 최종 승패 결정 테스트 케이스 추가 (승, 무, 패)
- [ ] 단위 테스트 중복 코드 제거
- [ ] 단위 테스트와 다른 메서드의 실행결과 의존 검토
