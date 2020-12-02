package edu.tuichu;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.tuichu.algorithm.MiniMaxAlgorithm;
import edu.tuichu.algorithm.TablutAlgorithm;
import edu.tuichu.heuristic.TuichuHeuristic;
import it.unibo.ai.didattica.competition.tablut.client.TablutClient;
import it.unibo.ai.didattica.competition.tablut.domain.Action;
import it.unibo.ai.didattica.competition.tablut.domain.Game;
import it.unibo.ai.didattica.competition.tablut.domain.GameAshtonTablut;
import it.unibo.ai.didattica.competition.tablut.domain.GameModernTablut;
import it.unibo.ai.didattica.competition.tablut.domain.GameTablut;
import it.unibo.ai.didattica.competition.tablut.domain.State;
import it.unibo.ai.didattica.competition.tablut.domain.StateBrandub;
import it.unibo.ai.didattica.competition.tablut.domain.StateTablut;
import it.unibo.ai.didattica.competition.tablut.domain.State.Turn;

/**
 * 
 * @author Tuichu team
 *
 */
public class TuichuTablutClient extends TablutClient {

	private int game;
	private Map<Turn,Turn> opponent, win, lose;

	public TuichuTablutClient(String player, String name, int gameChosen, int timeout, String ipAddress) throws UnknownHostException, IOException {
		super(player, name, timeout, ipAddress);
		game = gameChosen;
		
		opponent = new HashMap<>();
		opponent.put(Turn.WHITE, Turn.BLACK);
		opponent.put(Turn.BLACK, Turn.WHITE);
		
		win = new HashMap<>();
		win.put(Turn.WHITE, Turn.WHITEWIN);
		win.put(Turn.BLACK, Turn.BLACKWIN);
		
		lose = new HashMap<>();
		lose.put(Turn.WHITE, Turn.BLACKWIN);
		lose.put(Turn.BLACK, Turn.WHITEWIN);
	}
	
	public TuichuTablutClient(String player, String name, int timeout, String ipAddress) throws UnknownHostException, IOException {
		this(player, name, 4, timeout, ipAddress);
	}
	
	public TuichuTablutClient(String player, int timeout, String ipAddress) throws UnknownHostException, IOException {
		this(player, "tuichu", 4, timeout, ipAddress);
	}

	public TuichuTablutClient(String player) throws UnknownHostException, IOException {
		this(player, "tuichu", 4, 60, "localhost");
	}


	public static void main(String[] args) throws UnknownHostException, IOException, ClassNotFoundException {
		int gametype = 4;
		String role = "";
		String name = "tuichu";
		String ipAddress = "localhost";
		int timeout = 60;
		// TODO: change the behavior?
		if (args.length < 1) {
			System.out.println("You must specify which player you are (WHITE or BLACK)");
			System.exit(-1);
		} else {
			System.out.println(args[0]);
			role = (args[0]);
		}
		if (args.length == 2) {
			System.out.println(args[1]);
			timeout = Integer.parseInt(args[1]);
		}
		if (args.length == 3) {
			ipAddress = args[2];
		}
		System.out.println("Selected client: " + args[0]);

		TuichuTablutClient client = new TuichuTablutClient(role, name, gametype, timeout, ipAddress);
		client.run();
	}

	@Override
	public void run() {

		try {
			this.declareName();
		} catch (Exception e) {
			e.printStackTrace();
		}

		State state;

		/*Game rules = null;
		switch (this.game) {
		case 1:
			state = new StateTablut();
			rules = new GameTablut();
			break;
		case 2:
			state = new StateTablut();
			rules = new GameModernTablut();
			break;
		case 3:
			state = new StateBrandub();
			rules = new GameTablut();
			break;
		case 4:
			state = new StateTablut();
			state.setTurn(State.Turn.WHITE);
			rules = new GameAshtonTablut(99, 0, "garbage", "fake", "fake");
			System.out.println("Ashton Tablut game");
			break;
		default:
			System.out.println("Error in game selection");
			System.exit(4);
		}
		TablutAlgorithm algorithm = new MiniMaxAlgorithm(rules);*/
		
		if(this.game != 4)
			throw new IllegalArgumentException("TuichuTablutClient only supports Ashton rules");
		//TablutAlgorithm algorithm = new MiniMaxAlgorithm(100, new MockHeuristic());
		TablutAlgorithm algorithm = new MiniMaxAlgorithm(4, new TuichuHeuristic(), getTimeout());
		
		List<int[]> pawns = new ArrayList<int[]>();
		List<int[]> empty = new ArrayList<int[]>();

		System.out.println("You are player " + this.getPlayer().toString() + "!");

		while (true) {
			try {
				this.read();
			} catch (ClassNotFoundException | IOException e1) {
				e1.printStackTrace();
				System.exit(1);
			}
			System.out.println("Current state:");
			state = this.getCurrentState();
			System.out.println(state.toString());
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				//ok
			}
			
			Turn player = this.getPlayer(),
				turn = state.getTurn();
			
			if(player.equals(turn)) {
				Action a = algorithm.getAction(state);
				if(a == null)
					throw new RuntimeException("No action selected");

				System.out.println("Choosen move: " + a.toString());
				try {
					this.write(a);
				} catch (ClassNotFoundException | IOException e) {
					System.out.println("An error occurred while writing the choosen move");
					e.printStackTrace();
				}
				pawns.clear();
				empty.clear();
			} else if (turn.equals(opponent.get(player))) {
				System.out.println("Waiting for your opponent move... ");
			} else if (turn.equals(win.get(player))) { // ho vinto
				System.out.println("YOU WIN!");
				System.exit(0);
			} else if (turn.equals(lose.get(player))) { // ho perso
				System.out.println("YOU LOSE!");
				System.exit(0);
			} else if (turn.equals(StateTablut.Turn.DRAW)) { // pareggio
				System.out.println("DRAW!");
				System.exit(0);
			}
		}

	}
}
