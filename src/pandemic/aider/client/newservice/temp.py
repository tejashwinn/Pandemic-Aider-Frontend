import smtplib
from random import random

s = smtplib.SMTP("smtp.gmail.com" , 587)  # 587 is a port number
# start TLS for E-mail security
s.starttls()
# Log in to your gmail account
s.login("1nh19me115.tejashwinu@gmail.com" , "cyMF8C@By0^5BoiN")
# otp = random.random(1000, 9999)
otp=100
otp = str(otp)

s.sendmail("1n9me115.tejashwinu@gamil.com" , "1nh19cs238.tejashwinu@gmail.com" , otp)
print("OTP sent succesfully..")
# close smtp session
s.quit()