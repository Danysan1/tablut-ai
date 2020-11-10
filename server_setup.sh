#!/bin/sh

echo 'Installing dependencies...'
sudo apt update
sudo apt install openjdk-8-jdk -y
sudo apt install ant -y

echo 'Cloning repo...'
git clone https://github.com/AGalassi/TablutCompetition.git
cd TablutCompetition/Tablut

echo 'Compiling...'
ant clean
ant compile

echo 'Starting Server...'
ant server

echo 'Testing random white player...'
ant randomwhite

echo 'Testing random black player...'
ant randomblack