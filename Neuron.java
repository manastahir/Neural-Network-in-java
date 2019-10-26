import java.util.Random;
import static java.lang.StrictMath.exp;

public class Neuron
{
    private static final double lr=0.055;

    // prev layer neurons
    Neuron[] inputs;

    //weights of the connections
    double[] weights;

    //neuron's output(0 <= x <= 1.0)
    double output;

    //error
    double delta;

    //bias
    double bias;

    Neuron()
    {

    }

    Neuron(Neuron[] prev_layer)
    {
        this.inputs=prev_layer;
        this.weights=new double[prev_layer.length];

        // randomly initializing bias
        Random r = new Random(System.currentTimeMillis());
        this.bias=-1 + (2) * r.nextDouble();

        //randomly initializing weights
        for(int i=0;i<prev_layer.length;i++)
            this.weights[i]=-1 + (2) * r.nextDouble();
    }

    public void respond()
    {
        double sum=this.bias;

        for(int i=0;i<inputs.length;i++)
            sum+=(this.inputs[i].output*weights[i]);

       this.output=this.Sigmoid(sum);
    }

    public void gradientDescent()
    {
      this.delta *= derivativeSigmoid();

      //back propagate
      for(int i=0;i<inputs.length;i++)
          this.inputs[i].delta += this.delta*this.weights[i];

      //Stochastic gradientdescent
      this.bias -= lr*this.delta;
      for(int i=0;i<inputs.length;i++)
          this.weights[i] -= lr*(this.delta*this.inputs[i].output);

      delta=0;
    }


    public double derivativeSigmoid()
    {
        return output*(1-output);
    }

    private double Sigmoid(double sum)
    {
        return (1.0/(1.0+exp(-sum)));
    }

}
