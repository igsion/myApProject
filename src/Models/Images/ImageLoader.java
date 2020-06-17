package Models.Images;

import Models.Cards.Card;
import Models.FileManagers.CardsFileManager;
import Models.FileManagers.HeroesFileManager;
import Models.Hero.Hero;
import Models.Logger;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Set;

public class ImageLoader {

    public static BufferedImage loadingPage , playground;
    public static BufferedImage[] heroImage;
    public static BufferedImage[] cardsImage;

    private static Toolkit tk = Toolkit.getDefaultToolkit();

    public static void loadImages(){
        try {
            loadingPage = ImageIO.read(new File("resource/loading-page.jpg"));
            playground = ImageIO.read(new File("resource/playground.jpg"));

            loadingPage = resize(loadingPage , tk.getScreenSize().height , tk.getScreenSize().width);
            playground = resize(playground , tk.getScreenSize().height , tk.getScreenSize().width);

            Set<Card> cardSet = CardsFileManager.getCardsFileManager().getCardsSet();
            cardsImage = new BufferedImage[100];
            for(Card card : cardSet){
                cardsImage[card.id] = resize(ImageIO.read(new File("resource/Cards/" + card.name + ".png"))
                        , tk.getScreenSize().height/3 , tk.getScreenSize().width/6);
            }
            heroImage = new BufferedImage[5];
            Set<Hero> heroSet = HeroesFileManager.getHeroesFileManager().getHeroesSet();
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

}
