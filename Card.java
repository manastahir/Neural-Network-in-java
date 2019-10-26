
// Basic data holder
public class Card
{
    //image pixels
    int[] inputs;

    //output as one hot vector (we assume a vector of length 10)
    int[] outputs;

    //label
    int label;

    public Card() {
    }

    public void imageLoad(int[] input)
    {
        inputs=new int[input.length];

       for(int i=0;i<input.length;i++)
           inputs[i]=input[i];
    }

    public void labelLoad(int label)
    {
        this.label=label;
        outputs=new int[10];
        for(int i=0;i<10;i++)
            outputs[i]=0;

        outputs[label]=1;
    }

}
