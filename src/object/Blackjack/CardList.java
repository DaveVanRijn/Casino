/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package object.blackjack;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 *
 * @author Dave van Rijn, Student 500714558, Klas IS202
 */
public class CardList implements List<Card> {

    private final List<Card> cards;

    public CardList() {
        cards = new ArrayList<>();
    }

    public boolean containsAce() {
        for (Card c : cards) {
            if (c.getFace().equals("Ace") && c.getValue() == 11) {
                return true;
            }
        }
        return false;
    }

    public Card getAce() {
        if (containsAce()) {
            for (Card c : cards) {
                if (c.getFace().equals("Ace") && c.getValue() == 11) {
                    return c;
                }
            }
        }
        return null;
    }

    public int getValue() {
        int value = 0;
        for (Card c : cards) {
            value += c.getValue();
            if (value > 21 && containsAce()) {
                getAce().setValue(1);
                return getValue();
            }
        }

        return value;
    }
    
    public void shuffle(){
        Collections.shuffle(cards);
    }

    @Override
    public int size() {
        return cards.size();
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public boolean contains(Object o) {
        Card card = (Card) o;
        for (Card c : cards) {
            if (c.toString().equals(card.toString())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Iterator<Card> iterator() {
        return cards.iterator();
    }

    @Override
    public Object[] toArray() {
        return cards.toArray();
    }

    @Override
    public boolean add(Card e) {
        return cards.add(e);
    }

    @Override
    public boolean remove(Object o) {
        return cards.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return cards.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends Card> c) {
        return cards.addAll(c);
    }

    @Override
    public boolean addAll(int index, Collection<? extends Card> c) {
        return cards.addAll(index, c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return cards.removeAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return cards.retainAll(c);
    }

    @Override
    public void clear() {
        cards.clear();
    }

    @Override
    public Card get(int index) {
        return cards.get(index);
    }

    @Override
    public Card set(int index, Card element) {
        return cards.set(index, element);
    }

    @Override
    public void add(int index, Card element) {
        cards.add(index, element);
    }

    @Override
    public Card remove(int index) {
        return cards.remove(index);
    }

    @Override
    public int indexOf(Object o) {
        return cards.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return cards.lastIndexOf(o);
    }

    @Override
    public ListIterator<Card> listIterator() {
        return cards.listIterator();
    }

    @Override
    public ListIterator<Card> listIterator(int index) {
        return cards.listIterator(index);
    }

    @Override
    public List<Card> subList(int fromIndex, int toIndex) {
        return cards.subList(fromIndex, toIndex);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (Card c : cards) {
            builder.append(c.toString());
            builder.append(" value: ");
            builder.append(c.getValue());
            builder.append("\n");
        }
        return builder.toString();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
