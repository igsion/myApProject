package Models.Images;

import Models.Cards.Card;
import Models.FileManagers.CardsFileManager;
import Models.FileManagers.HeroesFileManager;
import Models.Hero.Hero;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Set;

public class ImageLoader {

    public static BufferedImage loadingPage, playground, divine, taunt;
    public static BufferedImage[] heroesImage;
    public static BufferedImage[] cardsImage;
    public static BufferedImage[] minionsImage;

    private static Toolkit tk = Toolkit.getDefaultToolkit();

    public static void loadImages(){
        try {
            loadingPage = ImageIO.read(new File("resource/loading-page.jpg"));
            playground = ImageIO.read(new File("resource/playground.jpg"));
            divine = ImageIO.read(new File("resource/Minion_Divine.png"));
            taunt = ImageIO.read(new File("resource/Minion_Taunt.png"));

            loadingPage = resize(loadingPage , tk.getScreenSize().height , tk.getScreenSize().width);
            playground = resize(playground , tk.getScreenSize().height , tk.getScreenSize().width);
            divine = resize(divine , tk.getScreenSize().height / 4 - tk.getScreenSize().height / 30
                    , tk.getScreenSize().width / 8 - tk.getScreenSize().width / 40 + 40);
            taunt = resize(taunt , 200 , 100);

            Set<Card> cardSet = CardsFileManager.getCardsFileManager().getCardsSet();
            Set<Hero> heroSet = HeroesFileManager.getHeroesFileManager().getHeroesSet();

            cardsImage = new BufferedImage[100];
            for(Card card : cardSet){
                cardsImage[card.id] = resize(ImageIO.read(new File("resource/Cards/" + card.name + ".png"))
                        , tk.getScreenSize().height/3 - tk.getScreenSize().height/30 ,
                        tk.getScreenSize().width/6 - tk.getScreenSize().width/40);
            }
            heroesImage = new BufferedImage[10];
            for(Hero hero : heroSet){
                heroesImage[hero.getId()] = resize(ImageIO.read(new File("resource/Heroes/" + hero.getName() + ".png"))
                        , tk.getScreenSize().height/4 - tk.getScreenSize().height/60  , tk.getScreenSize().width/6);
            }
            minionsImage = new BufferedImage[100];
            for(Card card : cardSet){
                if(card.type.equals("minion")){
                    minionsImage[card.id] = resize(ImageIO.read(new File("resource/Minions/" + card.name + ".png"))
                            , tk.getScreenSize().height/3 - tk.getScreenSize().height/30 ,
                            tk.getScreenSize().width/6 - tk.getScreenSize().width/40);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
//            System.exit(1);
        }
    }

    private static BufferedImage resize(BufferedImage img, int height, int width) {
        Image tmp = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = resized.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();
        return resized;
    }

    private static BufferedImage rotateImageByDegrees(BufferedImage img, double angle) {

        double rads = Math.toRadians(angle);
        double sin = Math.abs(Math.sin(rads)), cos = Math.abs(Math.cos(rads));
        int w = img.getWidth();
        int h = img.getHeight();
        int newWidth = (int) Math.floor(w * cos + h * sin);
        int newHeight = (int) Math.floor(h * cos + w * sin);

        BufferedImage rotated = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = rotated.createGraphics();
        AffineTransform at = new AffineTransform();
        at.translate((newWidth - w) / 2f, (newHeight - h) / 2f);

        int x = w / 2;
        int y = h / 2;

        at.rotate(rads, x, y);
        g2d.setTransform(at);
        g2d.drawImage(img, 0, 0, null);
        g2d.dispose();

        return rotated;
    }

}
