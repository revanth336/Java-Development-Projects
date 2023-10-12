import javax.swing.*;  
import java.awt.*;  
import java.awt.event.*;  
import java.lang.Exception; 
import java.util.Timer;
import java.util.TimerTask; 
class login extends JFrame implements ActionListener  
{  
    JButton b1;  
    JPanel newPanel;  
    JLabel userLabel, passLabel;  
    final JTextField  textField1, textField2;  
    login()  
    {     
        userLabel = new JLabel();  
        userLabel.setText("    Username :");      
        textField1 = new JTextField(15);      
        passLabel = new JLabel();  
        passLabel.setText("    Password :");        
        textField2 = new JPasswordField(8);     
        b1 = new JButton("   SUBMIT   ");  
        newPanel = new JPanel(new GridLayout(3, 1));  
        newPanel.add(userLabel);     
        newPanel.add(textField1);  
        newPanel.add(passLabel);    
        newPanel.add(textField2);   
        newPanel.add(b1);          
        add(newPanel, BorderLayout.CENTER);  
        b1.addActionListener(this);    
        setTitle("Login Form ");         
    }   
    public void actionPerformed(ActionEvent ae)     
    {  
        String userValue = textField1.getText();        
        String passValue = textField2.getText();       
        if(!passValue.equals(""))
            new OnlineTestBegin(userValue); 
        else{
            textField2.setText("Enter Password");
            actionPerformed(ae);
        }
    }     
}  
class OnlineTestBegin extends JFrame implements ActionListener  
{  
    JLabel l;  
    JLabel l1;  
    JRadioButton jb[]=new JRadioButton[6];  
    JButton b1,b2,log;  
    ButtonGroup bg;  
    int count=0,current=0,x=1,y=1,now=0;  
    int m[]=new int[10];  
    Timer timer = new Timer();  
    OnlineTestBegin(String s)  
    {      
        super(s); 
        l=new JLabel();
        l1 = new JLabel();  
        add(l);
        add(l1);  
        bg=new ButtonGroup();  
        for(int i=0;i<5;i++)  
        {  
            jb[i]=new JRadioButton();     
            add(jb[i]);  
            bg.add(jb[i]);  
        }  
        b1=new JButton("Save and Next");  
        b2=new JButton("Save for later");  
        b1.addActionListener(this);  
        b2.addActionListener(this);  
        add(b1);add(b2);  
        set();  
        l.setBounds(30,40,1000,20);
        l1.setBounds(20,20,1000,20);
        jb[0].setBounds(50,80,1000,20);  
        jb[1].setBounds(50,110,1000,20);  
        jb[2].setBounds(50,140,1000,20);  
        jb[3].setBounds(50,170,1000,20);  
        b1.setBounds(95,240,160,30);  
        b2.setBounds(270,240,160,30);  
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
        setLayout(null);  
        setLocation(250,100);  
        setVisible(true);  
        setSize(1000,350);     
        timer.scheduleAtFixedRate(new TimerTask() {
            int i = 600;
            public void run() {  
                l1.setText("Time left: " + i);
                i--;   
                if (i < 0) {
                    timer.cancel();
                    l1.setText("Time Out");                     
                } 
            }
        }, 0, 1000);        
    }  
    public void actionPerformed(ActionEvent e)  
    {          
        if(e.getSource()==b1)  
        {  
            if(check())  
                count=count+1;  
            current++;  
            set();    
            if(current==9)  
            {  
                b1.setEnabled(false);  
                b2.setText("Result");  
            }  
        }  
        if(e.getActionCommand().equals("Save for later"))  
        {  
            JButton bk=new JButton("Review"+x);  
            bk.setBounds(480,20+30*x,100,30);  
            add(bk);  
            bk.addActionListener(this);  
            m[x]=current;  
            x++;  
            current++;  
            set();    
            if(current==9)  
                b2.setText("Result");  
            setVisible(false);  
            setVisible(true);  
        }  
        for(int i=0,y=1;i<x;i++,y++)  
        {  
        if(e.getActionCommand().equals("Review"+y))  
        {  
            if(check())  
                count=count+1;  
            now=current;  
            current=m[y];  
            set();  
            ((JButton)e.getSource()).setEnabled(false);  
            current=now;  
        }  
        }      
        if(e.getActionCommand().equals("Result"))  
        {  
            if(check())  
                count=count+1;  
            current++;  
            JOptionPane.showMessageDialog(this,"Score ="+count);  
            System.exit(0);  
        }  
    }  
    void set()  
    {  
        jb[4].setSelected(true);  
         if(current==0)  
    {  
        l.setText("Que1: What does AI stand for?");
        jb[0].setText("Artificial Intelligence");
        jb[1].setText("Advanced Inference");
        jb[2].setText("Automated Interaction");
        jb[3].setText("All of the above");
    }  
    if(current==1)  
    {  
        l.setText("Que2: Which programming language is commonly used for machine learning?");
        jb[0].setText("Java");
        jb[1].setText("Python");
        jb[2].setText("C++");
        jb[3].setText("JavaScript");
    }  
    if(current==2)  
    {  
        l.setText("Que3: What is the primary goal of machine learning?");
        jb[0].setText("Automate tasks");
        jb[1].setText("Create intelligent machines");
        jb[2].setText("Learn from data and improve performance");
        jb[3].setText("All of the above");
    }  
    if(current==3)  
    {  
        l.setText("Que4: What is the process of training a machine learning model on existing data?");
        jb[0].setText("Supervised learning");
        jb[1].setText("Unsupervised learning");
        jb[2].setText("Reinforcement learning");
        jb[3].setText("Feature extraction");
    }  
    if(current==4)  
    {  
        l.setText("Que5: Which machine learning technique is used for classification problems?");
        jb[0].setText("Regression");
        jb[1].setText("Clustering");
        jb[2].setText("Decision trees");
        jb[3].setText("Dimensionality reduction");
    }
    // Add two more questions below
    if(current==5)  
    {  
        l.setText("Que6: What is the role of a neural network's activation function?");
        jb[0].setText("To determine the learning rate");
        jb[1].setText("To introduce randomness into the model");
        jb[2].setText("To define the output of a neuron");
        jb[3].setText("To specify the number of layers in the network");
    }
    if(current==6)  
    {  
        l.setText("Que7: In reinforcement learning, what is the reward signal used for?");
        jb[0].setText("To specify the number of states in the environment");
        jb[1].setText("To determine the agent's initial position");
        jb[2].setText("To guide the learning process by indicating the desirability of actions");
        jb[3].setText("To compute the value of the state");
    }
    if(current==7)  
    {  
        l.setText("Que8: Which machine learning technique is suitable for finding patterns in unlabeled data?");
        jb[0].setText("Supervised learning");
        jb[1].setText("Unsupervised learning");
        jb[2].setText("Reinforcement learning");
        jb[3].setText("Semi-supervised learning");
    }
    // Add two more questions below
    if(current==8)  
    {  
        l.setText("Que9: What is overfitting in machine learning?");
        jb[0].setText("It occurs when a model performs well on the training data but poorly on unseen data.");
        jb[1].setText("It is a technique to reduce model complexity.");
        jb[2].setText("It is a type of supervised learning.");
        jb[3].setText("It is the same as underfitting.");
    }
    if(current==9)  
    {  
        l.setText("Que10: What is a decision tree in machine learning?");
        jb[0].setText("A tree-like graph that represents decisions and their consequences.");
        jb[1].setText("A type of neural network architecture.");
        jb[2].setText("A reinforcement learning algorithm.");
        jb[3].setText("A clustering technique.");
    }
        l.setBounds(30,40,450,20);  
        for(int i=0,j=0;i<=90;i+=30,j++)  
            jb[j].setBounds(50,80+i,200,20);  
    }  
    boolean check()  
    {  
        if(current==0)  
            return(jb[1].isSelected());  
        if(current==1)  
            return(jb[1].isSelected());  
        if(current==2)  
            return(jb[2].isSelected());  
        if(current==3)  
            return(jb[0].isSelected());  
        if(current==4)  
            return(jb[2].isSelected());  
        if(current==5)  
            return(jb[3].isSelected());  
        if(current==6)  
            return(jb[1].isSelected());  
        if(current==7)  
            return(jb[3].isSelected());  
        if(current==8)  
            return(jb[2].isSelected());  
        if(current==9)  
            return(jb[2].isSelected());  
        return false;  
    }    
} 
class OnlineExam  
{  
    public static void main(String args[])  
    {  
        try  
        {  
            login form = new login();  
            form.setSize(400,150);  
            form.setVisible(true);  
        }  
        catch(Exception e)  
        {     
            JOptionPane.showMessageDialog(null, e.getMessage());  
        }  
    }  
} 