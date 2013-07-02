package com.base.engine;

public class MainComponent {

	/**
	 * @param args
	 */
	public static int WIDTH = 800;
	public static int HEIGHT = 600;
	public static String TITLE = "3d Engine";
	private boolean isRunning;
	public static final double FRAME_CAP = 5000.0;
	
	private Game game;
	public MainComponent() {
		System.out.println(RenderUtil.getOpenGLVersion());
		RenderUtil.initGraphics();
		isRunning = false;
		game = new Game();
	}
	public void start() {
		if(isRunning)
			return;
		run();
	}
	public void stop() {
		if(!isRunning)
			return;
		isRunning = false;
	}
	public void run() {
		isRunning = true;
		int frames = 0;
		int frameCounter = 0;
		final double frameTime = 1.0 / FRAME_CAP;
		long lastTime = Time.getTime();
		double unprocessedTime = 0;
		while(isRunning) {
			boolean render = false;
			long startTime = Time.getTime();
			long passedTime = startTime -lastTime;
			lastTime = startTime;
			
			unprocessedTime += passedTime / (double)Time.SECOND;
			frameCounter += passedTime;
			while(unprocessedTime > frameTime) {
				render = true;
				unprocessedTime -= frameTime;
				if(Window.isCloseRequested()) 
					stop();
				Time.setDelta(frameTime);
				game.input();
				Input.update();
				game.update();
				if(frameCounter >= Time.SECOND) {
					System.out.println(frames);
					frames = 0;
					frameCounter = 0;
				}
			}
			if(render){
				render();
				frames++;
			}
				
			else {
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		cleanUp();
	}
	public void render() {
		RenderUtil.clearScreen();
		game.render();
		Window.Render();
	}
	public void cleanUp() {
		Window.dispose();
	}
	public static void main(String[] args) {
		Window.CreateWindow(WIDTH, HEIGHT, TITLE);
		MainComponent game = new MainComponent();
		game.start();
	}

}
