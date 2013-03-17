import socket
from time import *
from datetime import datetime, timedelta




UDP_IP = "192.168.150.1" #indirizzo access point locale
UDP_PORT = 50005


filename1 = 'lg_p500_udp.txt'#
filename2 = 'galaxy_s1_1_udp.txt'#
filename3 = 'galaxy_s1_2_udp.txt'#1
def get_fd(filename): #return file descriptor for 3 phone txt
        logfile = open(filename,"w+")
        return logfile
initial_time={'remote':None,'local':None}       
log1=get_fd(filename1)  
log2=get_fd(filename2)
log3=get_fd(filename3)
          
sock = socket.socket(socket.AF_INET,socket.SOCK_DGRAM) # UDP
sock.bind((UDP_IP, UDP_PORT))
          
while True:
        data, addr = sock.recvfrom(1024) # buffer size is 1024 bytes
        dati=data.split()
        local_ts = float(datetime.now().strftime('%s.%f'))*1000 #ms
        remote_ts=float(dati[0])
        if not initial_time['remote']:
                initial_time['local'] = local_ts
                initial_time['remote']  = remote_ts
        skew = (remote_ts - initial_time['remote']) - (local_ts-initial_time['local'] ) 
        log3.write('%f\t%d\t%d\t%f batteria:%s \n' % (local_ts, remote_ts ,skew, local_ts-initial_time['local'], dati[1] ) )
        log3.flush()
                
        print "received message:", data
