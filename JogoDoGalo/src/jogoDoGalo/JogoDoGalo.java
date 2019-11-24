package jogoDoGalo;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;




public class JogoDoGalo extends JFrame {	

	final int WINDOW_WIDTH = 540;
	final int WINDOW_HEIGHT = 340;

	private static final long serialVersionUID = 1L;
	
	private Grelha aGrelha = new Grelha( new ImageIcon("art/grelha.png"), new Point(0,0));
	private char jogador = 'X';
	private int jogosEfetuados = 0;
	private int vitoriasX = 0;
	private int vitoriasO = 0;
	private int empates = 0;
	
	private static final Color cor = new Color(255, 255, 255);
	private static final Font font = new Font("Roman", Font.PLAIN, 20);

	public JogoDoGalo(){
		super( "Jogo do Galo" ); // título da janela
		setSize( WINDOW_WIDTH, WINDOW_HEIGHT );
		setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE ); // desliga programa quando fechas janela	
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		double width = screenSize.getWidth();
		double height = screenSize.getHeight();		
		
		setLocation((int)width / 2 - WINDOW_WIDTH / 2, (int)height / 2 - WINDOW_HEIGHT / 2);
		PainelDesenho painel = new PainelDesenho( );			
		painel.setBackground( Color.DARK_GRAY );			
		getContentPane().add( painel, BorderLayout.CENTER );
		
		// activar os listeners
		OuveRato or = new OuveRato();
		
		painel.addMouseListener( or );
		painel.addMouseMotionListener( or );
	}
		
	private class PainelDesenho extends JPanel {
		// isto só cá está para não dar um warning
		private static final long serialVersionUID = 1L;

		public void paintComponent( Graphics g ){
			super.paintComponent( g );
			aGrelha.desenhar( g );	
			
			// desenhar strings
			g.setColor(cor);		
			g.setFont(font);
			g.drawString("Jogador " + jogador, 320, 30);
			g.drawString("Partidas jogadas: " + jogosEfetuados , 320, 90);
			g.drawString("Vitórias X: " + vitoriasX, 320, 115);
			g.drawString("Vitórias O: " + vitoriasO, 320, 140);
			g.drawString("Empates: " + empates, 320, 165);
		}
	}				
		
	class OuveRato extends MouseAdapter implements MouseMotionListener {
		
		public void mousePressed( MouseEvent e ){  
			
			Point local = e.getPoint(); // ponto do MouseEvent				
			
			int x = -1;
			int y = -1;
			
			// conforme o local da grelha em que se carrega com o rato vai atribuir coordenadas a x e y
			if(local.x > 7 && local.x < 92 )
				y = 0;
			else if (local.x > 108 && local.x < 193)
				y = 1;
			else if (local.x > 207 && local.x < 293)
				y = 2;
			
			if(local.y > 10 && local.y < 85 )
				x = 0;
			else if (local.y > 109 && local.y < 188)
				x = 1;
			else if (local.y > 210 && local.y < 288)
				x = 2;
			
			if(x != -1 && y != -1 && eJogadaValida(aGrelha.getJogadas(), x, y)) {
				aGrelha.getJogadas()[x][y] = jogador; // escreve jogada, não devia ser um set e não um get?
				mudaJogador();
				repaint();
				
				char estado = terminou(aGrelha.getJogadas());
				int opcao;
				// se o jogo terminou, aumenta contador conforme resultado, imprime resultado e dá opção para jogar de novo ou sair
				if(estado != 'C') {
					jogador = 'X';			// primeiro a jogar sempre X
					++jogosEfetuados;
					String fim;
					if(estado == 'E') {
						fim = "Empate.";
						++empates;
					}
					else if (estado == 'X') {
						fim = "Vitória de X.";
						++vitoriasX;
					}
					else {
						fim = "Vitória de O.";
						++vitoriasO;
					}
					opcao = JOptionPane.showConfirmDialog(null, fim + " Quer jogar de novamente?", "Fim de jogo", 
							JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
					if(opcao == JOptionPane.YES_OPTION) {
						aGrelha.limpaJogo();
						repaint();
					} else
						System.exit(0);
				}								
			}				
		}	
	}
		
	private void mudaJogador() {
		if(jogador == 'O')
			jogador = 'X';
		else
			jogador = 'O';
	}	
	
	// Indica se uma jogada nas coordenadas (x,y) é válida. 
	// é válida se a matriz tiver um espaço e não X ou O
	public boolean eJogadaValida(char jogo[][], int x, int y) {			
		return jogo[x][y] == ' ';		
	}
		
	/* Método que indica o estado do jogo.
	 * Returns:
	 * 	X - Vitória de X
	 * 	O - Vitória de O
	 * 	E - Empate
	 *  C - Jogo ainda não acabou, deve continuar
	*/
	public char terminou(char jogo[][]) {			
		
		// Vitória de X
		if((jogo[0][0] == 'X' && jogo[1][0] == 'X' && jogo[2][0] == 'X') ||
		   (jogo[0][1] == 'X' && jogo[1][1] == 'X' && jogo[2][1] == 'X') ||
		   (jogo[0][2] == 'X' && jogo[1][2] == 'X' && jogo[2][2] == 'X') ||
		   (jogo[0][0] == 'X' && jogo[0][1] == 'X' && jogo[0][2] == 'X') ||
		   (jogo[1][0] == 'X' && jogo[1][1] == 'X' && jogo[1][2] == 'X') ||
		   (jogo[2][0] == 'X' && jogo[2][1] == 'X' && jogo[2][2] == 'X') ||
		   (jogo[0][0] == 'X' && jogo[1][1] == 'X' && jogo[2][2] == 'X') ||
		   (jogo[0][2] == 'X' && jogo[1][1] == 'X' && jogo[2][0] == 'X'))							
			return 'X';		
			
		// Vitória de O
		if((jogo[0][0] == 'O' && jogo[1][0] == 'O' && jogo[2][0] == 'O') ||
		   (jogo[0][1] == 'O' && jogo[1][1] == 'O' && jogo[2][1] == 'O') ||
		   (jogo[0][2] == 'O' && jogo[1][2] == 'O' && jogo[2][2] == 'O') ||
		   (jogo[0][0] == 'O' && jogo[0][1] == 'O' && jogo[0][2] == 'O') ||
		   (jogo[1][0] == 'O' && jogo[1][1] == 'O' && jogo[1][2] == 'O') ||
		   (jogo[2][0] == 'O' && jogo[2][1] == 'O' && jogo[2][2] == 'O') ||
		   (jogo[0][0] == 'O' && jogo[1][1] == 'O' && jogo[2][2] == 'O') ||
		   (jogo[0][2] == 'O' && jogo[1][1] == 'O' && jogo[2][0] == 'O')) 					
			return 'O';					
		
		// Empate
		if(jogo[0][0] != ' ' && jogo[0][1] != ' ' && jogo[0][2] != ' ' && 
		   jogo[1][0] != ' ' && jogo[1][1] != ' ' && jogo[1][2] != ' ' &&
		   jogo[2][0] != ' ' && jogo[2][1] != ' ' && jogo[2][2] != ' ')								
			return 'E';		
		
		return 'C';
	} 
		
	public static void main(String[] args) {			
			JogoDoGalo jogo = new JogoDoGalo();
			jogo.setVisible(true);			
		}	
}