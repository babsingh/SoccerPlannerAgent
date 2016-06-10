import java.util.Vector;

//
//	File:			Memory.java
//	Author:		Krzysztof Langner
//	Date:			1997/04/28
//

class Memory 
{
    //---------------------------------------------------------------------------
    // This constructor:
    // - initializes all variables
    public Memory()
    {
    }


    //---------------------------------------------------------------------------
    // This function puts see information into our memory
    public void store(VisualInfo info)
    {
	m_info = info;
    }

    //---------------------------------------------------------------------------
    // This function looks for specified object
    public ObjectInfo getObject(String name) 
    {
	if( m_info == null )
	    waitForNewInfo();

	for(int c = 0 ; c < m_info.m_objects.size() ; c ++)
	    {
		ObjectInfo object = (ObjectInfo)m_info.m_objects.elementAt(c);
		if(object.m_type.compareTo(name) == 0)
		    return object;
	    }												 

	return null;
    }


    //---------------------------------------------------------------------------
    // This function waits for new visual information
    public void waitForNewInfo() 
    {
	// first remove old info
	m_info = null;
	// now wait until we get new copy
	while(m_info == null)
	    {
		// We can get information faster then 75 miliseconds
		try
		    {
			Thread.sleep(SIMULATOR_STEP);
		    }
		catch(Exception e)
		    {
		    }
	    }
    }
    
    public Vector<PlayerInfo> getPlayers() {
    	Vector<PlayerInfo> players = m_info.getPlayerList();
    	if (null != players) {
    		return players;
    	}
    	return null;
    }
    
    public Vector<GoalInfo> getGoals() {
    	Vector<GoalInfo> goals = m_info.getGoalList();
    	if (null != goals) {
    		return goals;
    	}
    	return null;
    }
    
    public Vector<BallInfo> getBall() {
    	Vector<BallInfo> ball = m_info.getBallList();
    	if (null != ball) {
    		return ball;
    	}
    	return null;
    }
    
    public Vector<FlagInfo> getFlags() {
    	Vector<FlagInfo> flags = m_info.getFlagList();
    	if (null != flags) {
    		return flags;
    	}
    	return null;
    }

    public Vector<LineInfo> getLines() {
    	Vector<LineInfo> lines = m_info.getLineList();
    	if (null != lines) {
    		return lines;
    	}
    	return null;
    }

    //===========================================================================
    // Private members
    volatile public VisualInfo	m_info;	// place where all information is stored
    final static int SIMULATOR_STEP = 100;
}

