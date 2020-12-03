#!/bin/sh

echo 'Installing dependencies...';
sudo apt update;
sudo apt install openjdk-8-jdk ant git -y || sudo apt install openjdk-11-jdk ant git -y;
ret=$?;

if [ $ret -neq 0 ]; then
	echo "ERROR: INSTALLATION FAILED WITH CODE $ret";
	return 1;
elif [ $1 -eq 'original' ]; then
	echo 'Cloning repo...';
	git clone https://github.com/AGalassi/TablutCompetition.git;
	cd TablutCompetition/Tablut;

	echo 'Compiling...';
	ant clean;
	ant compile;

	#echo 'Starting Server...';
	#ant server;

	#echo 'Testing random white player...';
	#ant randomwhite;

	#echo 'Testing random black player...';
	#ant randomblack;

	echo 'To start the server:          cd TablutCompetition/Tablut && ant server';
	echo 'To start random white player: cd TablutCompetition/Tablut && ant randomwhite';
	echo 'To start random black player: cd TablutCompetition/Tablut && ant randomblack';
	return 0;
else
	cd Tablut;

	echo 'Compiling...';
	ant clean;
	ant compile;

	echo 'To start the server:          cd Tablut && ant server';
	echo 'To start random white player: cd Tablut && ant randomwhite';
	echo 'To start random black player: cd Tablut && ant randomblack';
	echo 'To start Tuichu white player: cd Tablut && ant tuichuwhite';
	echo 'To start Tuichu black player: cd Tablut && ant tuichublack';
fi