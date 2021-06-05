<p align="left">
	<img width="240" src="https://raw.githubusercontent.com/dsckiet/resources/master/dsckiet-logo.png" />
	<h2 align="left"> Covid Tracker App </h2>
	<h4 align="left"> Covid Tracker is an Android App that provides to its users, information about current COVID-19 case counts at India as well as Global level using various forms of statistical data interpretation methods. <h4>
</p>

---
[![DOCS](https://img.shields.io/badge/Documentation-see%20docs-green?style=for-the-badge&logo=appveyor)](INSERT_LINK_FOR_DOCS_HERE) 
  [![UI ](https://img.shields.io/badge/User%20Interface-Link%20to%20UI-orange?style=for-the-badge&logo=appveyor)](INSERT_UI_LINK_HERE)


## Functionalities
- [ ] Information of Total, Active, Recovered & Deceased Case counts at India & Global levels.
- [ ] Timely notifications of data updates (currently set to 2 times a day).
- [ ] Users can view details at country level in Global section, and State & district level in India section.
- [ ] Pie Charts and Line Graph are used to depict cummulative information at Global & India levels respectively.

## Upcoming Features
- [ ] An Interactive Map of India to enhance the User experience.
- [ ] Section devoted to show Vaccination & Testing statistics to the users.
- [ ] Ability for users to be able to download & share their COVID-19 vaccination certificates from within the app.
- [ ] Get timely notifications for your scheduled vaccination dates.

<br>


## Instructions to run

* Pre-requisites:
	-  Android Studio v4.0+
	-  A working Android physical device or emulator with USB debugging enabled

* Directions to setup/install
	- Clone this repository to your local folder using Git bash:
	```bash
	git clone https://github.com/dsckiet/covid-tracker-android-app.git
	```
	- Open this project from Android Studio
	- Connect to an Android physical device or emulator
	- To install the app into your device, run the following using command line tools
	```bash
	gradlew installDebug
	```

* Directions to execute
	-  To launch hands free, run the following using command line tools
	```bash
	adb shell monkey -p com.dsckiet.covid_tracker_android_app -c android.intent.category.LAUNCHER 1
	```

<br>

## Contributors

* [Anshul Gupta](https://github.com/Anshul1507)
* [Aditya](https://github.com/ydasc815)
* [Vidit Jha](http://github.com/jhavidit)
* [Manvendra](https://github.com/MANNU-BOT)



<br>
<br>

<p align="center">
	Made during 🌙 by DSC KIET
</p>
