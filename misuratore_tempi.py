#!/usr/bin/env python



from scapy.all import* #oggetto scapy
from time import *
from datetime import datetime, timedelta
import os

filename = 'lg_p500_scarica_ntp5_mod.txt'#150.6 not charge
filename2 = 'galaxy_s2_1.txt'
filename3='galaxy_s2_2'
addresses = [#TODO impostare ip e mac 
	{'ip': '192.168.150.9', 'mac':'5D:DA:D4:69:05:0F'},
        {'ip': '192.168.150.6', 'mac':'98:0C:82:7E:46:2B'},
	{'ip': '192.168.150.3', 'mac':'98:0C:82:7E:73:79'}
]
#impostare maschera
static_arp_cmd = 'ip n add %s lladdr %s dev wlan0 nud permanent'

initial_time={'remote':None,'local':None}
initial_time2={'remote':None,'local':None}
initial_time3={'remote':None,'local':None}

def get_ts(ip, logfile,initial_time):
	
	''' get ip address and name of the file
	I will write on the file a string formatted as follow:
	local_timestamp \t remote_timestamp \t rtt '''
	p1 = IP(dst=ip)/ICMP(type=13)
	print "mando  richiesta a %s" % ip
	ans = sr1(p1, timeout=5)
	if not ans:
		return
	if logfile==None:
		return
	rtt = (ans.time-p1.time)*1000# ms
	#remote_ts = ans.ts_ori
	remote_ts = ans.ts_rx #millisecondi da mezzanotte
	local_ts = float(datetime.now().strftime('%s.%f'))*1000 #ms
	#if not initial_time['remote']:
	if not initial_time['remote']:
		initial_time['local'] = local_ts
		initial_time['remote']	= remote_ts
	if (remote_ts-initial_time['remote'])<0:#in questo modo evito che a mezzanotte ans.ts_ori riparte da 0
                remote_ts+=86400000
	skew = (remote_ts - initial_time['remote']) - (local_ts-initial_time['local'] ) 
	if logfile:
		logfile.write('%f\t%f\t%d\t%d\t%f\n' % (local_ts, rtt, remote_ts ,skew, local_ts-initial_time['local'] ) )
		logfile.flush()

	
	
def main():
	#open file
	logfile = open(filename,"w+")
	logfile2 = open(filename2,"w+")
	logfile3= open(filename3,"w+")
	# iproute2 entry statica arp ip n add os.command system ()
	for a in addresses:
		os.system(static_arp_cmd % (a['ip'], a['mac']) )
	start = now = datetime.now()
	end = start + timedelta(hours=14)  
	
	while(now < end):
		now = datetime.now()
		for a in addresses:
			#if a['ip']=='192.168.150.6' :
			#	get_ts(a['ip'], logfile)
			#else: 
			#	get_ts(a['ip'],logfile2, initial_time1503)
			#get_ts(a['ip'],None)
			if a['ip']=='192.168.150.9' :
				temp=initial_time
				log=logfile
			if a['ip']=='192.168.150.6' :
				temp=initial_time2
				log=logfile2
			if a['ip']=='192.168.150.3' :
				temp=initial_time3
				log=logfile3
			get_ts(a['ip'],None)
			get_ts(a['ip'],None)
			get_ts(a['ip'],None)
			get_ts(a['ip'],None)
			get_ts(a['ip'],log,temp)
					
			#get_ts(a.ts,ip None)	
		#	sleep(5)
		sleep(300)# ogni 5 minuti   mi sveglio 
	logfile.close()
	#logfile2.close()

if __name__ == '__main__':
	main()

