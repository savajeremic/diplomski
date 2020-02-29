# diplomski
PROJEKTOVANJE I KREIRANJE KOMPLETNOG ANGULAR – SPRING VEB SISTEMA ZA PRODAJU VIDEO IGARA  

raider.sql ima preko 2000KB, tako da treba povecati dozvoljenu maksimalnu velicinu prilikom importovanja baze.  
za phpmyadmin, treba otici u C:\xampp\php\php.ini i izmeniti, iz koda koji je prikazan ispod, u vise od 2M(difoltna vrednost, izmeniti u recimo 8M) i pritom restartovati server kako bi radilo.  

; Maximum allowed size for uploaded files.  
; http://php.net/upload-max-filesize  
upload_max_filesize=8M  


# setup
-
Angular - fontend\raider-client>npm install ---> ng serve --open  
Spring boot - clean and build/build project ---> run

# users
-
role: admin  
email: admin@adminson.com  
password: admin96  

role: user
email: testcart@gmail.com
password: testcart



role: user  
email: testcart1@gmail.com
password: testcart1

role: user  
email: proba@gmail.com
password: proba123
