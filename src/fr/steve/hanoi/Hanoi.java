package fr.steve.hanoi;

import javax.swing.JFrame;

import fr.julienbeguier.pf.Parameter;
import fr.julienbeguier.pf.Parameter.ParameterCallback;
import fr.julienbeguier.pf.ParameterFinder;
import fr.stevecohen.hanoi.gui.GraphicConstants;
import fr.stevecohen.hanoi.gui.MainFrame;

public class Hanoi extends JFrame {
	private static final long serialVersionUID = -8938260467873863578L;

	public static void main(String[] args) {
		Parameter level = new Parameter("level", 1, "-lvl", "-level");
		Parameter start = new Parameter("start", 1, "-start");
		HanoiParametersCallback callback = new HanoiParametersCallback();
		level.setCallback(callback);
		start.setCallback(callback);
		
		ParameterFinder pFinder = new ParameterFinder(args, level, start);
		pFinder.processArguments();

		MainFrame mf = new MainFrame("Tour de Hanoï");
		mf.init();
	}

	static class HanoiParametersCallback implements ParameterCallback {

		@Override
		public void onFind(Parameter parameter) {
			if (parameter.getParamId().equals("level")) {
				String arg = parameter.getValueList().get(0);
				try {
					int level = Integer.parseInt(arg);
					if (level >= GraphicConstants.LEVEL_MIN && level <= GraphicConstants.LEVEL_MAX)
						GraphicConstants.NUMBER_OF_PIECES = level;
					else
						System.err.println("The level must be between " + GraphicConstants.LEVEL_MIN + " and " + GraphicConstants.LEVEL_MAX);
				}catch (NumberFormatException e) {
					System.err.println(arg + " is not a number. Default level will be used : " + GraphicConstants.NUMBER_OF_PIECES);
				}
			}
			if (parameter.getParamId().equals("start")) {
				String arg = parameter.getValueList().get(0);
				try {
					int start = Integer.parseInt(arg);
					if (start >= GraphicConstants.POSITION_MIN && start <= GraphicConstants.POSITION_MAX) {
						GraphicConstants.START_ROW = start;
					}else {
						System.err.println("The start position must be between " + GraphicConstants.POSITION_MIN + " and " + GraphicConstants.POSITION_MAX);
					}
				}catch (NumberFormatException e) {
					System.err.println(arg + " is not a number. Default start position will be used : " + GraphicConstants.START_ROW);
				}
			}
		}

		@Override
		public void onError(Parameter parameter) {

		}
	}
}