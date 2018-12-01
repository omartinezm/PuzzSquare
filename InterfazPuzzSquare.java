package interfaz;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;

import java.util.*;

public class InterfazPuzzSquare extends JFrame implements ActionListener
{
	private static final long serialVersionUID = 1L;
	
	private static final String DESORDENAR="DESORDENAR";

	private Integer[] numeros;
	
	private JButton[] bloques;
	
	private JLabel labelMovimientos;
	
	private JButton desordenar;
	
	private Integer posision;
	
	private Integer movimientos;
	
	public InterfazPuzzSquare( )
	{
		setTitle("PuzzSquare");
		setResizable(false);
		JPanel panelNumeros=new JPanel();
		GridLayout gridbag = new GridLayout(4,4,0,0 );
        panelNumeros.setLayout( gridbag );
		bloques=new JButton[16];
		numeros=new Integer[16];
		for(int i=1;i<17;i++)
		{
			numeros[i-1]=i;
			bloques[i-1]=new JButton(""+numeros[i-1]);
			bloques[i-1].setBackground(Color.BLUE);
			bloques[i-1].setForeground(Color.WHITE);
			bloques[i-1].setEnabled(false);
			bloques[i-1].setFocusable(false);
			bloques[i-1].setPreferredSize(new Dimension(60, 60));
			bloques[i-1].setActionCommand(""+(i-1));
			bloques[i-1].addActionListener(this);
			bloques[i-1].setFocusable(false);
			bloques[i-1].setFont(new Font("Arial", Font.BOLD, 12));
			panelNumeros.add(bloques[i-1]);
		}
		colocarRojo(15);
		posision=15;
		panelNumeros.setFocusable(true);
		panelNumeros.requestFocusInWindow();
		///////////////////////
		panelNumeros.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
				//System.out.println(e.getKeyCode());		
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				//System.out.println(e.getKeyCode());
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				int keyCode = e.getKeyCode();
				if(keyCode==38 || keyCode==75)
		        {
					if(posision>3)
					{
						posision-=4;
						moverFichas(""+posision,true);
					}
		        }
		        if(keyCode==40 || keyCode==74)
		        {
		        	if(posision<12)
					{
						posision+=4;
						moverFichas(""+posision,true);
					}
		        }
		        if(keyCode==39 || keyCode==76)
		        {
		        	if(posision%4<3)
					{
						posision++;
						moverFichas(""+posision,true);
					}
		        }
		        if(keyCode==37 || keyCode==72)
		        {
		        	if(posision%4>0)
					{
						posision--;
						moverFichas(""+posision,true);
					}
		        }
		        if(keyCode==10)
		        {
		        	reiniciarJuego();
		        }
			}
		});
		getContentPane( ).add( panelNumeros, BorderLayout.NORTH );
		///////////////////////
		UIManager.getDefaults().put("Button.disabledText",Color.WHITE);
		desordenar = new JButton("Desordenar");
		desordenar.setFocusable(false);
		desordenar.setActionCommand(DESORDENAR);
		desordenar.addActionListener(this);
		JPanel panelTemp=new JPanel();
		Random ran = new Random();
		int veces = ran.nextInt(10)+1;
		movimientos=veces;
		labelMovimientos = new JLabel("");
		desordenar(veces);
		while(finalizo())
		{
			desordenar(veces);
		}
		labelMovimientos = new JLabel("Movimientos: "+veces);
		panelTemp.add(desordenar);
		panelTemp.add(labelMovimientos);
		setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		getContentPane( ).add(panelTemp, BorderLayout.SOUTH);
		this.setSize(300, 310);
		movimientos=veces;
	}
	public static void main(String[] args)
	{
		InterfazPuzzSquare inter = new InterfazPuzzSquare();
		inter.setVisible(true);
	}
	public boolean finalizo()
	{
		int i=0;
		while(i<16)
		{
			if(!(bloques[i].getText().equals(""+(i+1))))
			{
				return false;
			}
			i++;
		}
		return true;
	}
	public void moverFichas(String etiqueta,boolean verfiricarAlAcabar)
	{
		if(!("16".equals(etiqueta)))
		{
			boolean cambio=false;
			int num=Integer.parseInt(etiqueta);
			if(num>3 && bloques[num-4].getText().equals("16"))
			{
				reiniciarBloque(num, -4);
				cambio=true;
			}
			if(num<12 && bloques[num+4].getText().equals("16"))
			{
				reiniciarBloque(num, +4);
				cambio=true;
			}
			if(num%4>0 && bloques[num-1].getText().equals("16"))
			{
				reiniciarBloque(num, -1);
				cambio=true;
			}
			if(num%4<3 && bloques[num+1].getText().equals("16"))
			{
				reiniciarBloque(num, +1);
				cambio=true;
			}
			if(cambio)
			{
				bloques[num].setText("16");
				bloques[num].setBackground(Color.RED);
				movimientos-=1;
				labelMovimientos.setText("Movimientos: "+movimientos);
			}
			
		}
		if(finalizo() && verfiricarAlAcabar)
		{
			JOptionPane.showMessageDialog( this, "Ha ganado", "Juego terminado", JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	@SuppressWarnings("rawtypes")
	public void desordenar(int veces)
	{
		
		int i=0;
		while(i<veces)
		{
			ArrayList opciones = encontrarOpciones(posision);
			Collections.shuffle(opciones);
			moverFichas(""+opciones.get(0),false);
			posision=(int)opciones.get(0);
			i++;
		}
		
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ArrayList encontrarOpciones(int entrada)
	{
		ArrayList temp = new ArrayList<>();
		if(entrada>3)
		{
			temp.add(entrada-4);
		}
		if(entrada<12)
		{
			temp.add(entrada+4);
		}
		if(entrada%4>0)
		{
			temp.add(entrada-1);
		}
		if(entrada%4<3)
		{
			temp.add(entrada+1);
		}
		return temp;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		String comando = e.getActionCommand( );
        if(comando.equals(DESORDENAR))
        {
        	reiniciarJuego();
        }
        else
		{
        	moverFichas(comando,true);
		}
	}
	
	public void colocarRojo(int pos)
	{
		bloques[pos].setBackground(Color.RED);
		bloques[pos].setForeground(Color.RED);
	}
	
	public void reiniciarBloque(int pos,int cor)
	{
		bloques[pos+cor].setText(""+bloques[pos].getText());
		bloques[pos+cor].setBackground(Color.BLUE);
		bloques[pos+cor].setForeground(Color.WHITE);
	}
	
	public void reiniciarJuego()
	{
		for(int i=1;i<17;i++)
		{
			numeros[i-1]=i;
			reiniciarBloque(i-1, 0);
			bloques[i-1].setText(""+numeros[i-1]);
		}
		bloques[15].setBackground(Color.RED);
		bloques[15].setForeground(Color.RED);
		posision=15;
		Random ran = new Random();
    	int veces = ran.nextInt(5)+1;
    	desordenar(veces);
		while(finalizo())
		{
			desordenar(veces);
		}
    	labelMovimientos.setText("Movimientos: "+veces);
    	movimientos=veces;
	}
}