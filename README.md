# *VolatilitySurf*
> At its core, VolatilitySurf is an open source stock options trading tool. It's a collaborative open source work-in-progress that allows the user to search by ticker for real-time stock option data, and interact with  implied volatility surface data visualizations.
<!-- > Live demo [_here_](https://www.example.com). --> <!-- Once a live deployment is available, include the link here. -->

## Table of Contents
- [*VolatilitySurf*](#volatilitysurf)
  - [Table of Contents](#table-of-contents)
  - [General Information](#general-information)
  - [Technologies Used](#technologies-used)
  - [Features](#features)
  - [Screenshots](#screenshots)
  - [Project Status](#project-status)
  - [Room for Improvement](#room-for-improvement)
  - [Acknowledgements](#acknowledgements)
  - [Contact](#contact)
<!-- * [License](#license) -->

<!-- PLACE BETWEEN SCREENSHOTS AND PROJECT STATUS WHEN IMPLEMENTING
* [Setup](#setup)
* [Usage](#usage)
-->

## General Information
<!--
- Provide general information about your project here.
- What problem does it (intend to) solve?
- What is the purpose of your project?
- Why did you undertake it?
-->
As fellow students in full stack development at Coding Dojo, collaborators Erik van Erp, David Moore, and Coren Frankel sought to build a first draft for open source volatility surface rendering--financial data visualizations that the exclusive Bloomberg Terminal service dominates. *VolatilitySurf* is a Java web app project that has evolved to encapsulate the volatility surface namesake with a minimalist ticker search function and real-time stock market options data presented in conventional format and with simple but sleek User Interface.
<!-- You don't have to answer all the questions - just the ones relevant to your project. -->


## Technologies Used
- Java 8
- Spring Boot - version 2.7.3
- JavaScript libraries: 
   + Plotly.js 
   + D3.js
   + jQuery
- Bootstrap
- [DataTables](https://datatables.net/)
- [Mboum Finance API](https://mboum.com/api/documentation#options)
- MySQL - version 8.0.28


## Features
<!-- List the ready features here: -->
- Minimalist home page with form search for stock tickers
- Real-time stock options data retrieved by user request
- Loading animation with pseudo-asynchronous progress reporting
- Error Handling & Validations with flash error messages 
- Upon valid request, hundreds of rows of stock option contracts are retrieved
- Successful search redirects to conventional stock options display (see [Yahoo!+Finance](https://finance.yahoo.com/quote/JNJ/options?p=JNJ))
- Options contracts are filtered by expiration and defaults to next impending expiry
- Tables are delineated and sorted into Calls and Puts
- DataTables provide table data sorting and other interactivity
- More on the way! (Rendered implied volatility surfaces coming soon)


## Screenshots
<!-- If you have screenshots, gifs, video demos you'd like to share, include them here. -->
![MiniDemo Version 0.3 gif](./img/v0.3Demo.gif)
>Version 0.3 displays sorted tables by dropdown expiration date and first authentic data visualizations of options contracts in scatterplot form.

![MiniDemo Version 0.4 gif](./img/v0.4Demo.gif)
>Version 0.4 demonstrates loading progress bar animation and new default table views split by Call and Put options contracts.

<!--
## Setup
What are the project requirements/dependencies? Where are they listed? A requirements.txt or a Pipfile.lock file perhaps? Where is it located?

Proceed to describe how to install / setup one's local environment / get started with the project.


## Usage
How does one go about using it?
Provide various use cases and code examples here.

`write-your-code-here`

-->
## Project Status
Project is: 🏗️ _in progress_

Active Contributors: 
  + [Erik van Erp](https://github.com/ErikvanErp) (Lead Full Stack Developer, UI/UX Designer)
  + [Coren Frankel](https://github.com/coren-frankel) (Full Stack Developer, DevOps)
<!-- _in progress_ / _complete_ / _no longer being worked on_. If you are no longer working on it, provide reasons why. -->


## Room for Improvement
<!-- Include areas you believe need improvement / could be improved. Also add TODOs for future development. -->

Room for improvement:
- The time it takes to successfully retrieve stock options varies from a couple of seconds to around 15-20 seconds depending on the amount of options contracts available for a stock.
- Provide more seamless access between recently searched tickers
- Rather than using the implied volatility value given from Mboum API, we intend to calculate our own implied volatility for improved accuracy in the data visualizations.

To do:
- Determine the missing factors to normalize implied volatility between Call & Put for one surface
- Isolate ideal filters for outlier data and implement interpolating algorithm for smooth surfaces
- Compile coordinates for d3/plotly to render as a real volatility surface plots
- Refactor search mechanism for speed
- Mimic Yahoo Finance "straddle" table view

Completed:
+ The lag after search initiation is due to the great size of the data being collected and will soon be augmented (i.e. loading animation, asynchronous loading). Currently we are calling and collecting many 100 or 1000 rows of data and immediately retrieving them for display. Thus in version 0.1 our drop down list meant to separate the options by date is unfinished, and the table stretches the page to an unreadable length. (v0.2 - 0.4)
+ Provide data filtering by options expiration date manipulated by drop down (v0.2)
+ Integrate loading animations where necessary (v0.2)

## Acknowledgements
<!-- I think you could do this section more justice Erik. Here's a "template/rough draft"-->
- This project was inspired by a series of conversations between Erik Van Erp and (a colleague) regarding the intriuging calculation and rendering of volatility surface plots as a means to identify opportune stock options trades...
In its inception, VolatilitySurf was a demonstration of our collective ability to research, digest, and develop from many points of ignorance (i.e. financial, technical, logistical) to produce a useful and openly available trading tool with a minimalist yet interactive design.
- This project was partially inspired by:
    + Collective open source financial tools like [OpenBB Terminal](https://www.openbb.co/products/terminal)
    + Stock options display by [Yahoo! Finance](https://finance.yahoo.com/quote/GOOG/options?p=GOOG)
    + The roughly $2K/month [Bloomberg Terminal](https://www.bloomberg.com/professional/contact-menu/?utm_source=bloomberg-menu&utm_medium=bcom&bbgsum=DG-WS-PROF-DEMO-bbgmenu)
- Many thanks to [David Moore](https://github.com/dav1dmoore) for his early contributions in researching and applying robust and dynamic data visualizations from the Plotly.js and D3.js libraries.


## Contact 
+ [Coren Frankel](https://linkedin.com/in/coren-frankel): feel free to [email me](mailto:coren.frankel@gmail.com)!
+ Erik van Erp
<!-- I'M NOT SURE WHAT YOU'D LIKE TO DISCLOSE, LINK TO? DISCUSS -->

<!-- Optional -->
<!-- ## License -->
<!-- This project is open source and available under the [... License](). -->

<!-- You don't have to include all sections - just the one's relevant to your project -->
