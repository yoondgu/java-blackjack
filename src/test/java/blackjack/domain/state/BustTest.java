package blackjack.domain.state;

import static org.assertj.core.api.Assertions.*;

import java.util.Arrays;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import blackjack.domain.card.Card;
import blackjack.domain.card.Cards;
import blackjack.domain.card.Denomination;
import blackjack.domain.card.Suit;
import blackjack.domain.game.MatchResult;

class BustTest {

	@Test
	@DisplayName("draw를 실행하면 에러가 발생한다.")
	void draw() {
		Cards cards = new Cards(Arrays.asList(Card.valueOf(Denomination.ACE, Suit.CLOVER),
			Card.valueOf(Denomination.JACK, Suit.CLOVER),
			Card.valueOf(Denomination.ACE, Suit.DIAMOND)));
		Bust bust = new Bust(cards);

		assertThatThrownBy(() -> bust.draw(Card.valueOf(Denomination.ACE, Suit.SPADE)))
			.isInstanceOf(IllegalStateException.class)
			.hasMessageContaining("턴이 종료된 상태입니다.");
	}

	@Test
	@DisplayName("stay를 실행하면 에러가 발생한다.")
	void stay() {
		Cards cards = new Cards(Arrays.asList(Card.valueOf(Denomination.ACE, Suit.CLOVER),
			Card.valueOf(Denomination.JACK, Suit.CLOVER),
			Card.valueOf(Denomination.ACE, Suit.DIAMOND)));
		Bust bust = new Bust(cards);

		assertThatThrownBy(() -> bust.stay())
			.isInstanceOf(IllegalStateException.class)
			.hasMessageContaining("턴이 종료된 상태입니다.");
	}

	@Test
	@DisplayName("턴을 더이상 진행할 수 없다.")
	void is_finished() {
		Cards cards = new Cards(Arrays.asList(Card.valueOf(Denomination.ACE, Suit.CLOVER),
			Card.valueOf(Denomination.JACK, Suit.CLOVER),
			Card.valueOf(Denomination.ACE, Suit.DIAMOND)));
		Bust bust = new Bust(cards);

		assertThat(bust.isFinished()).isTrue();
	}

	@Test
	@DisplayName("상대가 blackjack이면 결과는 LOSE이다.")
	void match_blackjack() {
		Cards standardCards = new Cards(Arrays.asList(Card.valueOf(Denomination.TWO, Suit.CLOVER),
			Card.valueOf(Denomination.JACK, Suit.CLOVER),
			Card.valueOf(Denomination.JACK, Suit.CLOVER)));
		Bust standard = new Bust(standardCards);

		Cards oppositeCards = new Cards(Arrays.asList(Card.valueOf(Denomination.ACE, Suit.SPADE),
			Card.valueOf(Denomination.JACK, Suit.SPADE)));
		BlackJack opposite = new BlackJack(oppositeCards);

		assertThat(standard.match(opposite)).isEqualTo(MatchResult.LOSE);
	}

	@Test
	@DisplayName("상대가 bust이면 결과는 DRAW이다.")
	void match_bust() {
		Cards standardCards = new Cards(Arrays.asList(Card.valueOf(Denomination.TWO, Suit.CLOVER),
			Card.valueOf(Denomination.JACK, Suit.CLOVER),
			Card.valueOf(Denomination.JACK, Suit.CLOVER)));
		Bust standard = new Bust(standardCards);

		Cards oppositeCards = new Cards(Arrays.asList(Card.valueOf(Denomination.TWO, Suit.SPADE),
			Card.valueOf(Denomination.JACK, Suit.SPADE),
			Card.valueOf(Denomination.JACK, Suit.SPADE)));
		Bust opposite = new Bust(oppositeCards);

		assertThat(standard.match(opposite)).isEqualTo(MatchResult.DRAW);
	}

	@Test
	@DisplayName("상대가 stay이면 결과는 LOSE이다.")
	void match_stay() {
		Cards standardCards = new Cards(Arrays.asList(Card.valueOf(Denomination.TWO, Suit.CLOVER),
			Card.valueOf(Denomination.JACK, Suit.CLOVER),
			Card.valueOf(Denomination.JACK, Suit.CLOVER)));
		Bust standard = new Bust(standardCards);

		Cards oppositeCards = new Cards(Arrays.asList(Card.valueOf(Denomination.ACE, Suit.SPADE),
			Card.valueOf(Denomination.NINE, Suit.SPADE)));
		Stay opposite = new Stay(oppositeCards);

		assertThat(standard.match(opposite)).isEqualTo(MatchResult.LOSE);
	}
}