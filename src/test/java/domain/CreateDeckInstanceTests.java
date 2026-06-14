package domain;

import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreateDeckInstanceTests {

	@Test
	public void createDeckInstance_emptyList_returnsEmptyDeck() {
		Random mockRandom = EasyMock.createMock(Random.class);
		EasyMock.replay(mockRandom);

		DeckBuilder deckBuilder = new DeckBuilder(mockRandom);
		Deck result = deckBuilder.createDeckInstance(List.of());

		assertEquals(List.of(), result.getCards());

		EasyMock.verify(mockRandom);
	}

	@Test
	public void createDeckInstance_oneCard_returnsDeckWithOneCard() {
		Card card1 = new Card("SKIP_1", CardType.SKIP);
		Random mockRandom = EasyMock.createMock(Random.class);

		EasyMock.replay(mockRandom);

		Deck result = new DeckBuilder(mockRandom).createDeckInstance(List.of(card1));

		assertEquals(List.of(card1), result.getCards());

		EasyMock.verify(mockRandom);
	}

	@Test
	public void createDeckInstance_twoDifferentCards_returnsDeckWithBothCards() {
		Card card1 = new Card("SKIP_1", CardType.SKIP);
		Card card2 = new Card("ATTACK_1", CardType.ATTACK);
		Random mockRandom = EasyMock.createMock(Random.class);

		EasyMock.expect(mockRandom.nextInt(2)).andReturn(0);

		EasyMock.replay(mockRandom);

		Deck result = new DeckBuilder(mockRandom).createDeckInstance(List.of(card1, card2));

		assertEquals(List.of(card2, card1), result.getCards());

		EasyMock.verify(mockRandom);
	}

	@Test
	public void createDeckInstance_threeCardsOneDuplicate_returnsDeckWithAllCards() {
		Card card1 = new Card("SKIP_1", CardType.SKIP);
		Card card2 = new Card("ATTACK_1", CardType.ATTACK);
		Random mockRandom = EasyMock.createMock(Random.class);

		EasyMock.expect(mockRandom.nextInt(GameConstants.THREE_CARDS)).andReturn(0);
		EasyMock.expect(mockRandom.nextInt(2)).andReturn(0);

		EasyMock.replay(mockRandom);

		Deck result = new DeckBuilder(mockRandom).createDeckInstance(List.of(card1, card2, card1));

		assertEquals(List.of(card2, card1, card1), result.getCards());

		EasyMock.verify(mockRandom);
	}

}
