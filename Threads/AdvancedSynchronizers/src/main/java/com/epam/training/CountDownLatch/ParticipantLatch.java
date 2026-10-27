package com.epam.training.CountDownLatch;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class ParticipantLatch extends Thread {
    private Integer participantId;
    private int currentLotPrice;
    private int cash;
    private AuctionLatch auctionLatch;
    public CountDownLatch latchEndBid = AuctionLatch.latchEndAuction;
    public CountDownLatch latchAuctionBegin = AuctionLatch.latchAuctionBegin;
    private boolean lost;
    public ParticipantLatch(int id, int lotPrice, int cash, AuctionLatch auctionLatch) {
        this.participantId = id;
        this.currentLotPrice = lotPrice;
        this.cash = cash;
        this.auctionLatch = auctionLatch;
    }
    public Integer getParticipantId() {
        return participantId;
    }
    public int getCurrentLotPrice() {
        return currentLotPrice;
    }
    public int getCash() {
        return cash;
    }
    public void setCash(int cash) {
        this.cash = cash;
    }
    public boolean isLost() {
        return lost;
    }
    @Override
    public void run() {
        try {
            if(cash < currentLotPrice) {
                lost = true;
                auctionLatch.getLatchAuctionBegin().countDown();
                System.out.println("Participant #" + participantId + " lost because (cash = " + cash + ") < (price = "
                        + currentLotPrice + ")");
                return;
            }
            System.out.println("Participant #" + participantId + " specifies a price. (cash = " + cash + ")");
            TimeUnit.MILLISECONDS.sleep(new Random().nextInt(2500));
            int delta = new Random().nextInt(10);
            currentLotPrice += delta;
            currentLotPrice = currentLotPrice < cash ? currentLotPrice : cash;
            System.out.println("Made a bet Participant #" + participantId + ": " + currentLotPrice);
            auctionLatch.getLatchAuctionBegin().countDown();
            auctionLatch.getLatchEndAuction().await();
        } catch(InterruptedException e) {
            throw new IllegalStateException();
        }
        System.out.println("Participant #" + participantId + " continue to work... (cash = " + cash + ") ");
    }
}
