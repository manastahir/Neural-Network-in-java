import java.io.IOException;
import java.util.List;

public class Base
{

    public static void main(String args[])
    {

        int epochs = 2;

        //Loading Training Data
        int label[]=MINSTReader.getLabels("training-labels.idx1-ubyte");
        List<int[]> images=MINSTReader.getImages("training-images.idx3-ubyte");

        //Creating input vectors and initalizing Neural networks
        Card cards[]=new Card[label.length];
        Network ann=new Network(MINSTReader.rows*MINSTReader.columns,170,10);
        for(int i=0;i<label.length;i++)
        {
            cards[i]=new Card();
            cards[i].imageLoad(images.get(i));
            cards[i].labelLoad(label[i]);
        }


        // Training
        for(int i =0; i<epochs;i++)
        {
            System.out.println("Epoch: " + i);
            ann.training(shuffle(cards));
        }

        //Loading Testing Data
        label=MINSTReader.getLabels("test-labels.idx1-ubyte");
        images=MINSTReader.getImages("test-images.idx3-ubyte");
        cards=new Card[label.length];
        for(int i=0;i<label.length;i++)
        {
            cards[i]=new Card();
            cards[i].imageLoad(images.get(i));
            cards[i].labelLoad(label[i]);
        }

        //UI
        InterFace face=new InterFace(MINSTReader.rows,MINSTReader.columns,cards,ann);

    }
    static Card[] shuffle(Card[] cards)
    {
        for (int i=0;i<cards.length;i++)
        {
         int a= (int)Math.random()*cards.length;
         int b= (int)Math.random()*cards.length;

         cards=swap(cards,a,b);
        }
        return cards;
    }

    static Card[] swap(Card[] cards,int a,int b)
    {
        Card tmp=cards[a];
        cards[a]=cards[b];
        cards[b]=tmp;
        return cards;
    }

}
