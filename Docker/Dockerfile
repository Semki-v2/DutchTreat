FROM ubuntu:14.04

MAINTAINER de1mos

ENV DEBIAN_FRONTEND noninteractive

ENV TOMCAT_ADMIN_USERNAME 'tomcat-admin'
ENV TOMCAT_ADMIN_PASSWORD 'zKGOMnVxOpU4bUk'

RUN echo '#initialization script' > /root/init.sh
RUN chmod +x /root/init.sh

RUN useradd -ms /bin/bash docker-user
RUN sudo usermod -aG sudo docker-user
RUN echo 'docker-user:12345678' | chpasswd

#SETUP SSH SERVER
RUN mkdir /var/run/sshd
RUN apt-get update && apt-get install -y openssh-server
RUN sed -i 's/PermitRootLogin without-password/PermitRootLogin yes/' /etc/ssh/sshd_config
# SSH login fix. Otherwise user is kicked off after login
RUN sed 's@session\s*required\s*pam_loginuid.so@session optional pam_loginuid.so@g' -i /etc/pam.d/sshd

RUN echo 'service ssh start' >> /root/init.sh
EXPOSE 22

#SETUP POSTGRESQL
RUN apt-get install -y postgresql postgresql-contrib
USER postgres
RUN /etc/init.d/postgresql start \
    && psql --command "CREATE USER tomcat_user WITH SUPERUSER PASSWORD 'tomcat_password';" \
    && createdb -O tomcat_user tomcat_db
USER root
RUN echo '[ ! "$(ls -A /var/lib/postgresql/9.3/main)" ] && echo "Database not exists" && mkdir /var/lib/postgresql/9.3/main && chown postgres:postgres /var/lib/postgresql/9.3/main && chmod 700 /var/lib/postgresql/9.3/main && cp -rp /var/lib/postgresql/9.3MAIN/* /var/lib/postgresql/9.3/main' >> /root/init.sh

RUN echo 'service postgresql start' >> /root/init.sh
RUN mkdir /var/lib/postgresql/9.3MAIN && cp -rp /var/lib/postgresql/9.3/main/* /var/lib/postgresql/9.3MAIN

# SETUP TOMCAT
#RUN apt-get install -y default-jdk
RUN apt-get install -y software-properties-common debconf-utils
RUN apt-add-repository ppa:webupd8team/java
RUN apt-get update
RUN echo "oracle-java8-installer shared/accepted-oracle-license-v1-1 select true" | debconf-set-selections
RUN apt-get install -y oracle-java8-installer

RUN groupadd tomcat && useradd -s /bin/false -g tomcat -d /opt/tomcat tomcat
RUN wget -O /root/tomcat-8.0.28.tar.gz http://mirror.sdunix.com/apache/tomcat/tomcat-8/v8.0.28/bin/apache-tomcat-8.0.28.tar.gz
RUN mkdir /opt/tomcat
RUN tar xvf /root/tomcat-8.0.28.tar.gz -C /opt/tomcat --strip-components=1
RUN chgrp -R tomcat /opt/tomcat/conf
RUN chmod g+rwx /opt/tomcat/conf
RUN chmod g+r /opt/tomcat/conf/*
RUN chown -R tomcat /opt/tomcat/work/ /opt/tomcat/temp/ /opt/tomcat/logs/
ENV NEW_TOMCAT_STR <user username=\"${TOMCAT_ADMIN_USERNAME}\" password=\"${TOMCAT_ADMIN_PASSWORD}\" roles=\"manager-gui,admin-gui\"\\\/>\\\n<\\\/tomcat-users>
RUN sed -i "s/<\/tomcat-users>/$NEW_TOMCAT_STR/g" /opt/tomcat/conf/tomcat-users.xml
COPY context.xml /opt/tomcat/conf/context.xml
RUN wget -O /opt/tomcat/lib/postgresql-jdbc4.jar https://jdbc.postgresql.org/download/postgresql-9.4-1204.jdbc4.jar

RUN echo '[ ! "$(ls -A /opt/tomcat/webapps/ROOT)" ] && echo "Tomcat webapps not exist" && chown tomcat:tomcat /opt/tomcat/webapps && chmod 700 /opt/tomcat/webapps && cp -rp /opt/tomcat/webappsMAIN/* /opt/tomcat/webapps' >> /root/init.sh
RUN echo 'chown tomcat:tomcat /opt/tomcat/logs && chmod 700 /opt/tomcat/logs' >> /root/init.sh
RUN mkdir /opt/tomcat/webappsMAIN && cp -rp /opt/tomcat/webapps/* opt/tomcat/webappsMAIN

RUN echo '/opt/tomcat/bin/startup.sh' >> /root/init.sh



RUN echo "tail -f /var/log/mylog.log"
CMD sh /root/init.sh
