#ifndef DESI_ESP_IMPORTANT_IMPORT_H
#define DESI_ESP_IMPORTANT_IMPORT_H

#include <jni.h>
#include <string>
#include <cstdlib>
#include <unistd.h>
#include <sys/mman.h>
#include <android/log.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <netdb.h>
#include <cerrno>
#include <sys/un.h>
#include <cstring>
#include <string>
#include <cmath>
#include "struct.h"




bool isESP = false;
bool isPlayerBox = false;
bool isPlayerLine = false;
bool isPlayerDist = false;
bool isPlayerHealth = false;
bool isPlayerName = false;
bool isPlayerHead = false;
bool isr360Alert = false;
bool isSkelton1 = false;

bool isGrenadeWarning = false;
bool isEnemyWeapon= false;
bool isPlayerename = false;

bool isBuggy = false;
bool isUAZ =false;
bool isTrike = false;
bool isBike = false;
bool isDacia = false;
bool isJet = false;
bool isBoat = false;
bool isBus = false;
bool isMirado = false;
bool isScooter =false;
bool isRony = false;
bool isSnowbike = false;
bool isSnowmobile = false;
bool isTempo = false;
bool isTruck = false;
bool isBRDM = false;
bool isLadaNiva = false;
bool isMonsterTruck = false;

bool isdot = false;
bool istwo = false;
bool isthree = false;
bool isfour = false;
bool isSixx = false;
bool isEightx = false;

bool isPan = false;
bool isAug = false;
bool isM762 =false;
bool isScar = false;
bool isM416 = false;
bool isM164A = false;
bool isMk47 = false;
bool isG36C = false;
bool isQBZ = false;
bool isAKM = false;
bool isGroza = false;
bool isDP28 = false;
bool isM249 = false;
bool isAWM = false;
bool isKar98K = false;
bool isUMP45 = false;
bool isThompson = false;

bool isAmmo7 = false;
bool isAmmo5 = false;

bool isBagL1 = false;
bool isBagL2 = false;
bool isBagL3 = false;
bool isHelmetL1 = false;
bool isHelmetL2 = false;
bool isHelmet3 = false;
bool isVestL1 = false;
bool isVestL2 =false;
bool isVestL3 = false;

bool isFirstAid = false;
bool isMedKit = false;

bool isGrenade = false;
bool isSmoke = false;
bool isMolotov =false;

bool isFlare = false;
bool isGhillie = false; 
bool isCheekPad = false; 
bool isCrate = false; 
bool isAirPlane = false;
bool isAirDrop = false;

bool EspCircle = false;
#endif
