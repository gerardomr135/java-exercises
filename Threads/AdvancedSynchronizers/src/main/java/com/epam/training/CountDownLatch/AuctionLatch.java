package com.epam.training.CountDownLatch;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Collectors;

public class AuctionLatch extends Thread {
    private List<ParticipantLatch> participants = new ArrayList<>();
    public static CountDownLatch latchEndAuction = new CountDownLatch(1);
    public static CountDownLatch latchAuctionBegin;
    public AuctionLatch(int numbersParticipant) {
        latchAuctionBegin = new CountDownLatch(numbersParticipant);
    }
    public void add(ParticipantLatch e) {
        participants.add(e);
    }
    public CountDownLatch getLatchEndAuction() {
        return latchEndAuction;
    }
    public CountDownLatch getLatchAuctionBegin() {
        return latchAuctionBegin;
    }
    @Override
    public void run() {
        try {
            System.out.println("Waiting for participants to bet...");
            latchAuctionBegin.await();
        } catch (InterruptedException e) {
            throw new IllegalStateException();
        }
        defineWinner();
        latchEndAuction.countDown();
    }
    private void defineWinner() {
        ParticipantLatch winner = Collections.max(participants
                .stream()
                .filter(o -> !o.isLost())
                .collect(Collectors.toList()), Comparator.comparingInt(ParticipantLatch::getCurrentLotPrice));
        System.out.println("Participant #" + winner.getParticipantId()
                + ", price: " + winner.getCurrentLotPrice() + " win!");
        winner.setCash(winner.getCash() - winner.getCurrentLotPrice());
    }
}
