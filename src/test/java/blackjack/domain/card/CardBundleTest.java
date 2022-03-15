package blackjack.domain.card;

import static blackjack.fixture.CardBundleGenerator.generateCardBundleOf;
import static blackjack.fixture.CardRepository.CLOVER10;
import static blackjack.fixture.CardRepository.CLOVER2;
import static blackjack.fixture.CardRepository.CLOVER3;
import static blackjack.fixture.CardRepository.CLOVER4;
import static blackjack.fixture.CardRepository.CLOVER5;
import static blackjack.fixture.CardRepository.CLOVER6;
import static blackjack.fixture.CardRepository.CLOVER7;
import static blackjack.fixture.CardRepository.CLOVER8;
import static blackjack.fixture.CardRepository.CLOVER_ACE;
import static blackjack.fixture.CardRepository.CLOVER_KING;
import static blackjack.fixture.CardRepository.DIAMOND_ACE;
import static blackjack.fixture.CardRepository.HEART_ACE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import blackjack.domain.game.Score;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class CardBundleTest {

    @DisplayName("of 팩토리 메소드는 두 장의 서로 다른 카드를 받아 인스턴스를 생성한다.")
    @Nested
    class OfTest {

        @DisplayName("of 팩토리 메소드는 언제나 두 장의 카드를 제공받아 그대로 인스턴스를 생성한다.")
        @Test
        void of_onlyInitsWithTwoCards() {
            CardBundle cardBundle = CardBundle.of(CLOVER2, CLOVER3);

            assertThat(cardBundle.getCards()).containsExactly(CLOVER2, CLOVER3);
            assertThat(cardBundle.getScore()).isEqualTo(Score.valueOf(5));
        }

        @DisplayName("of 팩토리 메소드에 서로 중복되는 카드를 제공하는 경우 예외가 발생한다.")
        @Test
        void of_duplicateCardsThrowsException() {
            assertThatThrownBy(() -> CardBundle.of(CLOVER2, CLOVER2))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("중복된 카드는 존재할 수 없습니다.");
        }
    }

    @DisplayName("addAndGenerate 메서드는 카드를 한 장 추가한 새로운 카드 패 인스턴스를 반환한다.")
    @Nested
    class AddAndGenerateTest {

        @DisplayName("addAndGenerate 메서드는 기존 카드 패를 변화시키지 않고 새로운 카드 패를 반환한다.")
        @Test
        void addAndGenerate_immutableCardBundle() {
            CardBundle cardBundle = generateCardBundleOf(CLOVER4, CLOVER5);
            CardBundle addedCardBundle = cardBundle.addAndGenerate(CLOVER6);

            assertThat(cardBundle.getCards()).containsExactly(CLOVER4, CLOVER5);
            assertThat(addedCardBundle.getCards())
                    .containsExactly(CLOVER4, CLOVER5, CLOVER6);
            assertThat(addedCardBundle.getScore())
                    .isEqualTo(Score.valueOf(15));
        }

        @DisplayName("addAndGenerate 메서드에 기존 카드 패에 존재하는 카드를 입력하는 경우 예외가 발생한다.")
        @Test
        void addAndGenerate_duplicateCardsThrowsException() {
            CardBundle cardBundle = generateCardBundleOf(CLOVER4, CLOVER5);
            assertThatExceptionOfType(IllegalArgumentException.class)
                    .isThrownBy(() -> cardBundle.addAndGenerate(CLOVER4))
                    .withMessage("중복된 카드는 존재할 수 없습니다.");
        }
    }

    @DisplayName("getScore 메서드는 에이스가 없을 때 각 카드가 지닌 값들을 그대로 합산하여 반환한다.")
    @Test
    void getScore_noAce() {
        CardBundle cardBundle = generateCardBundleOf(CLOVER4, CLOVER5);

        Score actual = cardBundle.getScore();
        Score expected = Score.valueOf(9);

        assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("getScore 메서드 실행시 에이스 1개가 포함된 경우에 대한 테스트")
    @Nested
    class SingleAceTest {

        @DisplayName("에이스를 11로 계산해도 21을 넘기지 않는다면 11로 계산한다.")
        @Test
        void getScore_aceIs11IfSumIsNotOver21() {
            CardBundle cardBundle = generateCardBundleOf(CLOVER_ACE, CLOVER4, CLOVER5);

            Score actual = cardBundle.getScore();
            Score expected = Score.valueOf(20);

            assertThat(actual).isEqualTo(expected);
        }

        @DisplayName("에이스를 11로 계산했을 때 21인 경우 11로 계산한다.")
        @Test
        void getScore_aceIs11IfSumIs21() {
            CardBundle cardBundle = generateCardBundleOf(CLOVER_ACE, CLOVER3, CLOVER7);

            Score actual = cardBundle.getScore();
            Score expected = Score.valueOf(21);

            assertThat(actual).isEqualTo(expected);
        }

        @DisplayName("에이스를 11로 계산했을 때 21을 넘는 경우 1로 계산한다.")
        @Test
        void getScore_aceIs1IfSumIsOver21() {
            CardBundle cardBundle = generateCardBundleOf(CLOVER_ACE, CLOVER4, CLOVER7);

            Score actual = cardBundle.getScore();
            Score expected = Score.valueOf(12);

            assertThat(actual).isEqualTo(expected);
        }
    }

    @DisplayName("getScore 메서드 실행시 에이스가 2개 이상 포함된 경우에 대한 테스트")
    @Nested
    class MultipleAceTest {

        @DisplayName("에이스 중 한 장이라도 11로 계산했을 때 21을 초과할 경우, 모든 에이스는 1로 계산한다.")
        @Test
        void getScore_allAceIs1IfSumCouldGoOver21() {
            CardBundle cardBundle =
                    generateCardBundleOf(CLOVER_ACE, HEART_ACE, DIAMOND_ACE, CLOVER8, CLOVER10);

            Score actual = cardBundle.getScore();
            Score expected = Score.valueOf(21);

            assertThat(actual).isEqualTo(expected);
        }

        @DisplayName("에이스 중 한 장을 11로 계산했을 때 21 이내인 경우, 한 장은 11로, 나머지는 1로 계산한다.")
        @Test
        void getScore_onlyOneAceIs11IfSumIsNotOver21() {
            CardBundle cardBundle =
                    generateCardBundleOf(CLOVER_ACE, HEART_ACE, DIAMOND_ACE, CLOVER2, CLOVER3);

            Score actual = cardBundle.getScore();
            Score expected = Score.valueOf(18);

            assertThat(actual).isEqualTo(expected);
        }
    }

    @DisplayName("isBlackjack 메서드 테스트")
    @Nested
    class IsBlackjackTest {

        @DisplayName("점수가 21이면서 두 장의 카드로 구성된 경우 true를 반환한다.")
        @Test
        void isBlackjack_returnTrueOn21WithTwoCards() {
            CardBundle twoCardsOf21 = generateCardBundleOf(CLOVER_ACE, CLOVER_KING);

            boolean actual = twoCardsOf21.isBlackjack();

            assertThat(actual).isTrue();
        }

        @DisplayName("점수가 21이지만 3장 이상으로 구성된 경우 false를 반환한다.")
        @Test
        void isBlackjack_returnFalseOn21WithMoreThanTwoCards() {
            CardBundle twoCardsOf21 = generateCardBundleOf(CLOVER3, CLOVER8, CLOVER_KING);

            boolean actual = twoCardsOf21.isBlackjack();

            assertThat(actual).isFalse();
        }

        @DisplayName("점수가 21이 아니면 false를 반환한다.")
        @Test
        void isBlackjack_returnFalseIfNot21() {
            CardBundle twoCardsOf20 = generateCardBundleOf(CLOVER10, CLOVER_KING);

            boolean actual = twoCardsOf20.isBlackjack();

            assertThat(actual).isFalse();
        }
    }

    @DisplayName("isBlackjackScore 메서드 테스트")
    @Nested
    class IsBlackjackScoreTest {

        @DisplayName("점수가 21이면서 두 장의 카드로 구성된 경우 true를 반환한다.")
        @Test
        void isBlackjackScore_returnTrueOnBlackjack() {
            CardBundle twoCardsOf21 = generateCardBundleOf(CLOVER_ACE, CLOVER_KING);

            boolean actual = twoCardsOf21.isBlackjackScore();

            assertThat(actual).isTrue();
        }

        @DisplayName("점수가 21이지만 3장 이상으로 구성된 경우에도 true를 반환한다.")
        @Test
        void isBlackjackScore_returnTrueOn21NonBlackjack() {
            CardBundle twoCardsOf21 = generateCardBundleOf(CLOVER3, CLOVER8, CLOVER_KING);

            boolean actual = twoCardsOf21.isBlackjackScore();

            assertThat(actual).isTrue();
        }

        @DisplayName("점수가 21이 아니면 false를 반환한다.")
        @Test
        void isBlackjackScore_returnFalseIfNot21() {
            CardBundle twoCardsOf20 = generateCardBundleOf(CLOVER10, CLOVER_KING);

            boolean actual = twoCardsOf20.isBlackjackScore();

            assertThat(actual).isFalse();
        }
    }

    @DisplayName("isBust 메서드 테스트")
    @Nested
    class IsBustTest {

        @DisplayName("점수가 21을 넘지 않으면 false를 반환한다.")
        @Test
        void isBust_returnFalseOn21OrLess() {
            CardBundle cardBundleOf21 = generateCardBundleOf(CLOVER_ACE, CLOVER_KING);

            boolean actual = cardBundleOf21.isBust();

            assertThat(actual).isFalse();
        }

        @DisplayName("점수가 21을 넘으면 true를 반환한다.")
        @Test
        void isBust_returnTrueOnOver21() {
            CardBundle cardBundleOver21 = generateCardBundleOf(CLOVER8, CLOVER10, CLOVER_KING);

            boolean actual = cardBundleOver21.isBust();

            assertThat(actual).isTrue();
        }
    }
}