package models;

import java.util.HashMap;

public class Board {

    private Vec2D topLeftCorner;
    private Vec2D dimensions;
    private int widthInTiles;
    private int heightInTiles;
    private Card lastHoveredCard;
    private Card[] cards;
    private HashMap<Integer, String> animalsImagesMap;
    private float cardWidth;
    private float cardHeight;
    private static final int MATH_KEY = 43;

    public Board(Vec2D topLeftCorner, Vec2D dimensions, int widthInTiles, int heightInTiles) {
        this.topLeftCorner = topLeftCorner;
        this.widthInTiles = widthInTiles;
        this.heightInTiles = heightInTiles;
        this.dimensions = dimensions;
        this.cardWidth = dimensions.getX() / widthInTiles;
        this.cardHeight = dimensions.getY() / heightInTiles;
        initCards();
    }

    private void initCards() {

        int areaInTiles = (int)(widthInTiles * heightInTiles);

        animalsImagesMap = new HashMap<>();
        animalsImagesMap.put(0, "TurtleCard.png");
        animalsImagesMap.put(1, "LionCard.png");
        animalsImagesMap.put(2, "ZebraCard.png");
        animalsImagesMap.put(3, "SharkCard.png");
        animalsImagesMap.put(4, "GiraffeCard.png");
        animalsImagesMap.put(5, "SnakeCard.png");
        animalsImagesMap.put(6, "EagleCard.png");
        animalsImagesMap.put(7, "CrabCard.png");  
        animalsImagesMap.put(8, "OctopusCard.png");
        animalsImagesMap.put(9, "TigerCard.png");
        animalsImagesMap.put(10, "CrocodileCard.png");
        animalsImagesMap.put(11, "ElephantCard.png");

        this.cards = new Card[areaInTiles];

        for(int i = 0; i < areaInTiles / 2; i++) {

            initCard(i);

            initCard(i);
        }
    }

    private void initCard(int i) {
        int cardIndex;
        do {
            cardIndex = (int)(Math.random() * this.cards.length);
        }
        while(this.cards[cardIndex] != null);
        
        Vec2D coord2D = translateTo2DCoords(cardIndex, widthInTiles, heightInTiles);

        int x = (int) coord2D.getX();
        int y = (int) coord2D.getY();
        float xCenter = topLeftCorner.getX() + cardWidth / 2.0f + (x * cardWidth);
        float yCenter = topLeftCorner.getY() + cardHeight / 2.0f + (y * cardHeight);

        Vec2D center = new Vec2D(xCenter, yCenter);
        this.cards[cardIndex] = new Card(center, new Vec2D(cardWidth, cardHeight), animalsImagesMap.get(i % 12));
    }

    private Vec2D translateTo2DCoords(int i, int width, int height) {
        return new Vec2D(i % width, i / width);
    }

    private int translateClickTo1DCoords(Vec2D clickCoords) {
        int x = (int) ((clickCoords.getX() - topLeftCorner.getX()) / cardWidth);
        int y = (int) ((clickCoords.getY() - topLeftCorner.getY()) / cardHeight);
        return widthInTiles * y + x % widthInTiles;
    }

    private boolean isValidClick(Vec2D clickCoords) {
        return clickCoords != null && clickCoords.getX() > topLeftCorner.getX()
                && clickCoords.getX() < topLeftCorner.getX() + dimensions.getX()
                && clickCoords.getY() > topLeftCorner.getY()
                && clickCoords.getY() < topLeftCorner.getY() + dimensions.getY();
    }

    public boolean hoverValidation(Vec2D coords) {
        return isValidClick(coords) && lockedCardsAmount() < 2 && !cards[translateClickTo1DCoords(coords)].isRemoved() && cards[translateClickTo1DCoords(coords)].isContained(coords);
    }

    public int refresh(float dt, Vec2D clickVec2d) {
        int refreshResult = 0;
        if (hoverValidation(clickVec2d)) {
            refreshResult = cards[translateClickTo1DCoords(clickVec2d)].lock();
        }
        int currentlyRevealedCardsAmount = revealedCardsAmount();
        int firstIndex = currentlyRevealedCardsAmount / MATH_KEY;
        int secondIndex = currentlyRevealedCardsAmount % MATH_KEY;
        if (currentlyRevealedCardsAmount > cards.length - 1) {
            if (!cards[firstIndex].getCardName().equals(cards[secondIndex].getCardName())) {
                cards[firstIndex].cover();
                cards[secondIndex].cover();
            } else {
                cards[firstIndex].remove();
                cards[secondIndex].remove();
            }
        } else if (currentlyRevealedCardsAmount > -1) {
            if (isValidClick(clickVec2d) && cards[secondIndex].isRevealed()
                    && secondIndex == translateClickTo1DCoords(clickVec2d) && cards[translateClickTo1DCoords(clickVec2d)].isContained(clickVec2d)) {
                cards[secondIndex].cover();
            }
        }
        for (Card card : cards) {
            card.transform(dt);
        }
        return refreshResult;
    }

    public int lockedCardsAmount() {
        int amount = 0;
        for (Card card : cards) {
            amount += (card.isLocked()) ? 1 : 0;
        }
        return amount;
    }

    public int revealedCardsAmount() {
        int data = -1;
        int index = 0;
        for (Card card : cards) {
            data += (card.isRevealed()) ? (data < 0) ? (index + 1) : MATH_KEY * index : 0;
            index++;
        }
        return data;
    }

    public Card[] getCards() {
        return cards;
    }

    public Vec2D getTopLeftCorner() {
        return topLeftCorner;
    }

    public Vec2D getDimensions() {
        return dimensions;
    }

    public void hover(Vec2D coords) {
        lastHoveredCard = cards[translateClickTo1DCoords(coords)];
        lastHoveredCard.hover();

    }

    public void unhover() {
        if(lastHoveredCard != null) {
            lastHoveredCard.unhover();
        }
    }

}
