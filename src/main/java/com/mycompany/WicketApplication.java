package com.mycompany;

import org.apache.wicket.Application;
import org.apache.wicket.ThreadContext;
import org.apache.wicket.core.util.string.ComponentRenderer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.protocol.http.WebApplication;

import static java.lang.System.*;


/**
 * Application object for your web application.
 * If you want to run this application without deploying, run the Start class.
 * 
 */
public class WicketApplication extends WebApplication
{
	/**
	 * @see org.apache.wicket.Application#getHomePage()
	 */
	@Override
	public Class<? extends WebPage> getHomePage()
	{
		return HomePage.class;
	}

	/**
	 * @see org.apache.wicket.Application#init()
	 */
	@Override
	public void init()
	{
		super.init();

		// add your configuration here


		//new Thread(new BackgroundThreadOne()).start();

		new Thread(new BackgroundThreadTwo()).start();

	}


	/**
	 * Raw thread
	 *
	 * Fails, no wicket on current thread exception
	 */
	class BackgroundThreadOne implements Runnable {
		public void run() {

			out.println("In backgroun thread ONE");

			try {
				Thread.sleep(2000);
			} catch (Exception ex) {
			  // no one cares.
			}


			MyPanel myPanel = new MyPanel("someId");
			CharSequence res = ComponentRenderer.renderComponent(myPanel);

			out.println("res = " + res);

		}
	}



	/**
	 * Thread with some setup
	 *
	 * Fails with

	 Exception in thread "Thread-4" java.lang.IllegalArgumentException: Argument 'requestCycle' may not be null.
	 at org.apache.wicket.util.lang.Args.notNull(Args.java:41)
	 at org.apache.wicket.Application.fetchCreateAndSetSession(Application.java:1568)
	 at org.apache.wicket.Session.get(Session.java:171)
	 at org.apache.wicket.Application$1.onInstantiation(Application.java:289)
	 at org.apache.wicket.application.ComponentInstantiationListenerCollection$1.notify(ComponentInstantiationListenerCollection.java:38)
	 at org.apache.wicket.application.ComponentInstantiationListenerCollection$1.notify(ComponentInstantiationListenerCollection.java:34)
	 at org.apache.wicket.util.listener.ListenerCollection.notify(ListenerCollection.java:80)
	 at org.apache.wicket.application.ComponentInstantiationListenerCollection.onInstantiation(ComponentInstantiationListenerCollection.java:33)
	 at org.apache.wicket.Component.<init>(Component.java:687)
	 at org.apache.wicket.MarkupContainer.<init>(MarkupContainer.java:121)
	 at org.apache.wicket.markup.html.WebMarkupContainer.<init>(WebMarkupContainer.java:52)
	 at org.apache.wicket.markup.html.WebMarkupContainer.<init>(WebMarkupContainer.java:44)
	 at org.apache.wicket.markup.html.panel.Panel.<init>(Panel.java:65)
	 at com.mycompany.MyPanel.<init>(MyPanel.java:15)
	 at com.mycompany.WicketApplication$BackgrounThreadTwo.run(WicketApplication.java:100)
	 at java.lang.Thread.run(Thread.java:745)

	 *
	 *
	 */
	class BackgroundThreadTwo implements Runnable {
		public void run() {

			out.println("In backgroun thread TWO");

			try {
				Thread.sleep(3000);
			} catch (Exception ex) {
				// no one cares.
			}


			// try and attach the wicket thread context.
			String key = Application.getApplicationKeys().iterator().next(); // do people really have multiple wicket apps in one .war?
			Application app = Application.get(key);

			out.println("app = " + app);
			ThreadContext.setApplication(app);



			MyPanel myPanel = new MyPanel("someId");
			CharSequence res = ComponentRenderer.renderComponent(myPanel);

			out.println("res = " + res);

		}
	}



}
