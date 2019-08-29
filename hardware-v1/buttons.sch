EESchema Schematic File Version 4
LIBS:usb2most-cache
EELAYER 30 0
EELAYER END
$Descr A4 11693 8268
encoding utf-8
Sheet 5 7
Title ""
Date ""
Rev ""
Comp ""
Comment1 ""
Comment2 ""
Comment3 ""
Comment4 ""
$EndDescr
$Comp
L Connector:Conn_01x14_Female J3
U 1 1 5D1CB31F
P 1600 1900
F 0 "J3" H 1492 975 50  0000 C CNN
F 1 "Conn_01x14_Female" H 1492 1066 50  0000 C CNN
F 2 "usb2most:1x14-2.00mm" H 1600 1900 50  0001 C CNN
F 3 "~" H 1600 1900 50  0001 C CNN
	1    1600 1900
	-1   0    0    1   
$EndComp
$Comp
L Connector:Conn_01x14_Female J4
U 1 1 5D1CC502
P 1600 4050
F 0 "J4" H 1492 3125 50  0000 C CNN
F 1 "Conn_01x14_Female" H 1492 3216 50  0000 C CNN
F 2 "usb2most:1x14-2.00mm" H 1600 4050 50  0001 C CNN
F 3 "~" H 1600 4050 50  0001 C CNN
	1    1600 4050
	-1   0    0    1   
$EndComp
Text GLabel 2300 1600 2    50   Input ~ 0
12Vlcd
Text GLabel 2300 1700 2    50   Input ~ 0
GNDlcd
Text GLabel 2300 1800 2    50   Input ~ 0
5Vlcd
Wire Wire Line
	1800 1600 2300 1600
Wire Wire Line
	2300 1700 1800 1700
Wire Wire Line
	1800 1800 2300 1800
Text GLabel 2300 2100 2    50   Input ~ 0
LCD_MOSI
Text GLabel 2300 2200 2    50   Input ~ 0
LCD_CS
Text GLabel 2300 2300 2    50   Input ~ 0
LCD_CLK
Text GLabel 2300 2400 2    50   Input ~ 0
LCD_CMD
Wire Wire Line
	1800 2100 2300 2100
Wire Wire Line
	1800 2200 2300 2200
Wire Wire Line
	1800 2300 2300 2300
Wire Wire Line
	2300 2400 1800 2400
Wire Wire Line
	1800 4650 2200 4650
$Comp
L Device:R R2
U 1 1 5D1D104C
P 2200 5100
F 0 "R2" H 2270 5146 50  0000 L CNN
F 1 "47k" H 2270 5055 50  0000 L CNN
F 2 "Resistor_SMD:R_0805_2012Metric" V 2130 5100 50  0001 C CNN
F 3 "~" H 2200 5100 50  0001 C CNN
	1    2200 5100
	1    0    0    -1  
$EndComp
$Comp
L Device:R R3
U 1 1 5D1D183B
P 2500 5100
F 0 "R3" H 2570 5146 50  0000 L CNN
F 1 "47k" H 2570 5055 50  0000 L CNN
F 2 "Resistor_SMD:R_0805_2012Metric" V 2430 5100 50  0001 C CNN
F 3 "~" H 2500 5100 50  0001 C CNN
	1    2500 5100
	1    0    0    -1  
$EndComp
$Comp
L Device:R R4
U 1 1 5D1D1A0C
P 2800 5100
F 0 "R4" H 2870 5146 50  0000 L CNN
F 1 "47k" H 2870 5055 50  0000 L CNN
F 2 "Resistor_SMD:R_0805_2012Metric" V 2730 5100 50  0001 C CNN
F 3 "~" H 2800 5100 50  0001 C CNN
	1    2800 5100
	1    0    0    -1  
$EndComp
$Comp
L Device:R R5
U 1 1 5D1D1BAE
P 3100 5100
F 0 "R5" H 3170 5146 50  0000 L CNN
F 1 "47k" H 3170 5055 50  0000 L CNN
F 2 "Resistor_SMD:R_0805_2012Metric" V 3030 5100 50  0001 C CNN
F 3 "~" H 3100 5100 50  0001 C CNN
	1    3100 5100
	1    0    0    -1  
$EndComp
$Comp
L Device:R R6
U 1 1 5D1D1D6F
P 3400 5100
F 0 "R6" H 3470 5146 50  0000 L CNN
F 1 "47k" H 3470 5055 50  0000 L CNN
F 2 "Resistor_SMD:R_0805_2012Metric" V 3330 5100 50  0001 C CNN
F 3 "~" H 3400 5100 50  0001 C CNN
	1    3400 5100
	1    0    0    -1  
$EndComp
Wire Wire Line
	1850 5400 2050 5400
Wire Wire Line
	3400 5400 3400 5250
Wire Wire Line
	3100 5250 3100 5400
Connection ~ 3100 5400
Wire Wire Line
	3100 5400 3400 5400
Wire Wire Line
	2800 5250 2800 5400
Connection ~ 2800 5400
Wire Wire Line
	2800 5400 3100 5400
Wire Wire Line
	2500 5250 2500 5400
Connection ~ 2500 5400
Wire Wire Line
	2500 5400 2800 5400
Connection ~ 2200 5400
Wire Wire Line
	2200 5400 2500 5400
Wire Wire Line
	2200 5250 2200 5400
Wire Wire Line
	2200 4950 2200 4650
Connection ~ 2200 4650
Wire Wire Line
	1800 4550 2500 4550
Wire Wire Line
	2500 4550 2500 4950
Wire Wire Line
	1800 4450 2800 4450
Wire Wire Line
	2800 4450 2800 4950
Wire Wire Line
	3100 4950 3100 4350
Wire Wire Line
	3100 4350 1800 4350
Wire Wire Line
	1800 4250 3400 4250
Wire Wire Line
	3400 4250 3400 4950
Wire Wire Line
	2200 4650 3700 4650
Wire Wire Line
	3400 4250 3700 4250
Connection ~ 3400 4250
Wire Wire Line
	3100 4350 3700 4350
Connection ~ 3100 4350
Wire Wire Line
	2800 4450 3700 4450
Connection ~ 2800 4450
Wire Wire Line
	3700 4550 2500 4550
Connection ~ 2500 4550
Wire Wire Line
	1800 4150 3700 4150
Wire Wire Line
	1800 4050 3700 4050
Wire Wire Line
	1800 3950 3700 3950
Wire Wire Line
	3700 3850 1800 3850
Wire Wire Line
	1800 3750 3700 3750
Wire Wire Line
	1800 3650 3700 3650
Text GLabel 2300 3350 2    50   Input ~ 0
GNDlcd
Wire Wire Line
	1800 3350 2200 3350
Wire Wire Line
	1800 3550 3700 3550
Wire Wire Line
	3700 3450 1800 3450
Text GLabel 2300 1300 2    50   Input ~ 0
TUN1
Text GLabel 2300 1400 2    50   Input ~ 0
TUN0
Wire Wire Line
	1800 1400 2300 1400
Wire Wire Line
	2300 1300 1800 1300
Text GLabel 3700 4650 2    50   Input ~ 0
COL0
Text GLabel 3700 4550 2    50   Input ~ 0
COL1
Text GLabel 3700 4450 2    50   Input ~ 0
COL2
Text GLabel 3700 4350 2    50   Input ~ 0
COL3
Text GLabel 3700 4250 2    50   Input ~ 0
COL4
Text GLabel 3700 4150 2    50   Input ~ 0
ROW0
Text GLabel 3700 4050 2    50   Input ~ 0
ROW1
Text GLabel 3700 3950 2    50   Input ~ 0
ROW2
Text GLabel 3700 3850 2    50   Input ~ 0
ROW3
Text GLabel 3700 3750 2    50   Input ~ 0
ROW4
Text GLabel 3700 3650 2    50   Input ~ 0
ROW5
Text GLabel 3700 3550 2    50   Input ~ 0
VOL0
Text GLabel 3700 3450 2    50   Input ~ 0
VOL1
Text GLabel 1850 5400 0    50   Input ~ 0
5Vlcd
$Comp
L Device:C C?
U 1 1 5D6BC03B
P 2050 5800
AR Path="/5D1C7EF0/5D6BC03B" Ref="C?"  Part="1" 
AR Path="/5D1CB12D/5D6BC03B" Ref="C53"  Part="1" 
F 0 "C53" H 1850 5700 50  0000 C CNN
F 1 "100n" H 1850 5800 50  0000 C CNN
F 2 "Capacitor_SMD:C_0805_2012Metric" H 2088 5650 50  0001 C CNN
F 3 "~" H 2050 5800 50  0001 C CNN
	1    2050 5800
	-1   0    0    1   
$EndComp
$Comp
L power:GND #PWR060
U 1 1 5D6BE4B3
P 2050 6150
F 0 "#PWR060" H 2050 5900 50  0001 C CNN
F 1 "GND" H 2055 5977 50  0000 C CNN
F 2 "" H 2050 6150 50  0001 C CNN
F 3 "" H 2050 6150 50  0001 C CNN
	1    2050 6150
	1    0    0    -1  
$EndComp
Wire Wire Line
	2050 5400 2050 5650
Connection ~ 2050 5400
Wire Wire Line
	2050 5400 2200 5400
Wire Wire Line
	2050 5950 2050 6150
$Comp
L power:GND #PWR0116
U 1 1 5D5FF450
P 2200 3050
F 0 "#PWR0116" H 2200 2800 50  0001 C CNN
F 1 "GND" H 2205 2877 50  0000 C CNN
F 2 "" H 2200 3050 50  0001 C CNN
F 3 "" H 2200 3050 50  0001 C CNN
	1    2200 3050
	-1   0    0    1   
$EndComp
Wire Wire Line
	2200 3350 2200 3050
Connection ~ 2200 3350
Wire Wire Line
	2200 3350 2300 3350
$EndSCHEMATC
