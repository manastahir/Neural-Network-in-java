
public class Network
{

    // Network with 2 layers (hidden, output)

    public Neuron inputLayer[],hiddenLayer[],outputLayer[];

    public Network(int numInput,int numHidden,int numOutput)
    {

        inputLayer=new Neuron[numInput];
        for(int i=0;i<numInput;i++)
            inputLayer[i]=new Neuron();

        hiddenLayer=new Neuron[numHidden];
        for(int j=0;j<numHidden;j++)
            hiddenLayer[j]=new Neuron(inputLayer);

        outputLayer=new Neuron[numOutput];
        for(int k=0;k<numOutput;k++)
            outputLayer[k]=new Neuron(hiddenLayer);

    }

    public int feedForward(int[] inputs)
    {
        int i, j, k;
        for (i = 0; i < inputLayer.length; i++)
            inputLayer[i].output = (inputs[i]);

        for (j = 0; j < hiddenLayer.length; j++)
            hiddenLayer[j].respond();

        for (k = 0; k < outputLayer.length; k++)
            outputLayer[k].respond();


        double max=Double.NEGATIVE_INFINITY;
        int index=-1;

        for(k=0;k<outputLayer.length;k++)
            if(max<outputLayer[k].output)
            {
                max=outputLayer[k].output;
                index=k;
            }

        return index;
    }

    public void backPropogate(int target[])
    {
        int i, j, k;

            for (k = 0; k < outputLayer.length; k++)
                outputLayer[k].delta = (outputLayer[k].output * Math.log(target[k]) + (1 - outputLayer[k].output) * Math.log(1 - target[k]));

            for (k = 0; k < outputLayer.length; k++)
                outputLayer[k].gradientDescent();

            for (j = 0; j < hiddenLayer.length; j++)
                hiddenLayer[j].gradientDescent();

    }

    public void training(Card[] trainingSet)
    {
        int n=trainingSet.length;

        for(int x=0; x<n ; x++)
        {
            this.feedForward(trainingSet[x].inputs);
            this.backPropogate(trainingSet[x].outputs);
        }
    }

    public int test(int[] inputs)
    {
        return this.feedForward(inputs);
    }



}
