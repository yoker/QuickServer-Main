/*
 * This file is part of the QuickServer library 
 * Copyright (C) QuickServer.org
 *
 * Use, modification, copying and distribution of this software is subject to
 * the terms and conditions of the GNU Lesser General Public License. 
 * You should have received a copy of the GNU LGP License along with this 
 * library; if not, you can download a copy from <http://www.quickserver.org/>.
 *
 * For questions, suggestions, bug-reports, enhancement-requests etc.
 * visit http://www.quickserver.org
 *
 */
package org.quickserver.net.client.loaddistribution.impl;

import java.util.List;
import java.util.logging.Logger;
import org.quickserver.net.client.ClientInfo;
import org.quickserver.net.client.loaddistribution.LoadPattern;
import org.quickserver.net.client.Host;
import org.quickserver.net.client.HostList;

/**
 *
 * @author Akshathkumar Shetty
 */
public class HashedLoadPattern implements LoadPattern {
	private static final Logger logger = Logger.getLogger(HashedLoadPattern.class.getName());
	
	private HostList hostList;	
	
	public HashedLoadPattern() {
		
	}
	
	public HostList getHostList() {
		return hostList;
	}
	public void setHostList(HostList hostList) {
		this.hostList = hostList;
	}

	public Host getHost(ClientInfo clientInfo) {
		List activeList = getHostList().getActiveList();
		
		if(activeList==null || activeList.isEmpty()) {
			logger.warning("No active list available to service requests");
			return null;
		}
		int size = activeList.size();
		
		if(clientInfo.getClientKey()==null) {
			throw new NullPointerException("ClientKey was null!");
		}
		
		int hash = clientInfo.getClientKey().hashCode();
		
		int mod = hash % size;
		if(mod<0) mod = mod*-1;
		
		return (Host) activeList.get(mod);
	}
	
}

