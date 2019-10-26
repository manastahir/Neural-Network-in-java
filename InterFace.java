import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InterFace
{

    JFrame frame;
    JButton grid[][], next, prev;
    int imagenum,x,y;
    Card Set[];
    JLabel Output;
    Network network;

    private class ButtonActionListner implements ActionListener
    {
        public void actionPerformed(ActionEvent ae)
        {
            if (ae.getSource() == next)
                imagenum++;

            else if (ae.getSource() == prev)
                imagenum--;

            int[] arr=(Set[imagenum].inputs);
            for(int i=0;i<y;i++)
                for(int j=0;j<y;j++)
                    painButton(grid[i][j],arr[i*y+j]);

            Output.setText("OUTPUT: "+String.valueOf(network.test(arr))+" ACTUAL: "+Set[imagenum].label);
        }
    }

    private class RoundedBorder implements Border
    {

        private int radius;


        RoundedBorder(int radius) {
            this.radius = radius;
        }


        public Insets getBorderInsets(Component c) {
            return new Insets(this.radius, this.radius+1, this.radius+2, this.radius);
        }


        public boolean isBorderOpaque() {
            return true;
        }


        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            g.drawRoundRect(x, y, width-1, height-1, radius, radius);
        }
    }

    public InterFace(int x,int y,Card[] set,Network network)
    {
        this.x=x;
        this.y=y;
        this.network=network;
        Set=set;
        frame=new JFrame();
        frame.setSize(585, 700);
        // frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        grid=new JButton[y][x];
        setComponents();
        frame.setVisible(true);
    }

    private void setComponents()
    {
        next=new JButton("NEXT");
        prev=new JButton("PREVIOUS");
        Output=new JLabel("X");
        JPanel mainPanel=new JPanel(new FlowLayout());
        int x_pos=0,y_pos=15;
        for(int i=0;i<y;i++)
        {
            //  y_pos+=10;
            for (int j = 0; j < x; j++)
            {
                grid[i][j] = new JButton();
                grid[i][j].setBounds(x_pos, y_pos, 30, 25);
                grid[i][j].setBorder(new RoundedBorder(7)); //10 is the radius
                grid[i][j].setBackground(Color.WHITE);
                grid[i][j].setEnabled(false);
                mainPanel.add(grid[i][j]);
                // x_pos+=10;
            }
        }

        next.addActionListener(new ButtonActionListner());
        prev.addActionListener(new ButtonActionListner());
        mainPanel.add(next);
        mainPanel.add(prev);
        mainPanel.add(Output);
        frame.setContentPane(mainPanel);
    }

    private void painButton(JButton b,int rgb)
    {
        /*Color c=null;
        if(rgb<125) c=new Color(255,255,255);
        else c=new Color(0,0,0);*/
        b.setBackground(new Color(rgb));
        b.setOpaque(true);
    }


}