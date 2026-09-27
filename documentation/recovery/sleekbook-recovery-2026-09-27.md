# HP ENVY m6 Sleekbook — SATA Diagnosis, Full Backup, and Network Recovery

Date: 2026-09-27

## System

- Machine: HP ENVY m6 Sleekbook
- Product number: E0L01UA#ABA
- System board ID: 1995
- BIOS: F.18
- Born-on date: 2013-09-15
- OS: Ubuntu
- Kernel: 7.0.0-31-generic
- SSD: Samsung SSD 870 EVO 500GB
- Root filesystem: ext4
- Root UUID: 8c73fd0f-f50b-4cb6-bb21-94be25df5c86
- EFI system partition: /dev/sda1
- Root partition: /dev/sda2
- Swap: /dev/sda3

Disk layout:

- /dev/sda1 — 1 GB FAT, /boot/efi
- /dev/sda2 — 447 GB ext4, /
- /dev/sda3 — 17.7 GB swap

## SATA Problem

Ubuntu reported intermittent SATA communication failures including:

- interface fatal error
- PHYRdyChg
- CommWake
- 10B8B
- Handshk
- WRITE FPDMA QUEUED failures

During later recovery testing, Linux also reported:

    ata1: SATA link down (SStatus 0 SControl 300)

The HP firmware also intermittently displayed:

    Boot Device Not Found
    Hard Disk (3F0)

On a subsequent boot, the SSD appeared again in the UEFI boot menu as:

    ubuntu (Samsung SSD 870 EVO 500GB)

This behavior is consistent with an intermittent SATA communication/path problem.

## SSD SMART Results

smartmontools was installed and the Samsung SSD was checked.

SMART overall-health result:

    PASSED

Important values:

- Reallocated sectors: 0
- Program failures: 0
- Erase failures: 0
- Runtime bad blocks: 0
- Uncorrectable errors: 0
- ECC errors: 0
- Power-on hours: 1520
- Power cycles: 851
- SSD temperature during initial check: 26 C

CRC_Error_Count changed during troubleshooting:

    561 -> 562 -> 564 -> 570

The CRC count increased while the SSD itself continued to report healthy media statistics. Together with the intermittent 3F0 boot failure and SATA link-down event, the SATA cable/connector/path is the primary hardware area to inspect or replace.

The exact replacement SATA cable part number has NOT yet been physically verified.

## Full System Backup

A full filesystem-level recovery backup was created before opening the laptop or replacing SATA hardware.

Backup server:

    Terryza
    192.168.1.1

SMB share:

    //192.168.1.1/TerryzaCloud

Backup directory:

    Sleekbook-FULL-2026-09-27

Terryza storage location:

    /mnt/terryza-cloud/cloud/Sleekbook-FULL-2026-09-27

Backup software:

    FSArchiver 0.8.9

Archive:

    sleekbook-filesystems.fsa

Archive ID:

    6abd42e3

Compression:

    zstd level 3

Filesystems included:

1. /dev/sda1
   - FAT
   - original size: 1.05 GB
   - used: 6.30 MB
   - UUID: 18215DCB

2. /dev/sda2
   - ext4
   - original size: 438.95 GB
   - used: 40.39 GB
   - UUID: 8c73fd0f-f50b-4cb6-bb21-94be25df5c86

FSArchiver result for both filesystems:

    errors=0

Final backup directory size:

    approximately 26 GB

The swap partition was not archived because swap contains no persistent user data.

The backup was made from the running Ubuntu system using FSArchiver's -A option. It is therefore a live filesystem backup, not a bit-for-bit offline disk image.

## Recovery Metadata Saved

The backup directory also contains:

- sda-partitions.sfdisk
- blkid.txt
- fstab
- packages.tsv
- smart-before.txt
- archive-info.txt
- efibootmgr.txt

These preserve the partition layout, filesystem identifiers, mount configuration, installed package information, SMART information, archive information, and UEFI boot configuration needed for recovery.

## Terryza Network Recovery System

A USB-independent recovery environment was built on Terryza.

TFTP root:

    /mnt/terryza-cloud/recovery/tftp

Files:

    ipxe.efi
    boot_script.ipxe
    sleekbook-recovery-vmlinuz
    sleekbook-recovery-initrd.img

iPXE UEFI loader size:

    1,163,776 bytes

Recovery initramfs size:

    approximately 86 MB

Recovery kernel size:

    approximately 17 MB

The recovery environment contains:

- fsarchiver
- mount.cifs
- sfdisk
- mkfs.ext4
- mkfs.fat
- mkswap
- blkid
- lsblk
- efibootmgr
- ip
- blockdev
- CIFS support
- efivarfs support
- Realtek r8169 Ethernet driver

No Wi-Fi recovery dependency is required.

## iPXE Boot Script

Terryza serves this recovery boot sequence:

    #!ipxe
    kernel tftp://192.168.1.1/sleekbook-recovery-vmlinuz sleekbook_recovery=1
    initrd tftp://192.168.1.1/sleekbook-recovery-initrd.img
    boot

## Terryza PXE Configuration

Terryza OpenWrt dnsmasq was configured to:

- enable TFTP
- use /mnt/terryza-cloud/recovery/tftp as TFTP root
- identify UEFI x86-64 PXE clients
- serve ipxe.efi to the firmware PXE client
- identify the subsequent iPXE client
- serve boot_script.ipxe to iPXE

A copy of the previous DHCP configuration was saved as:

    /etc/config/dhcp.pre-sleekbook-pxe

## Sleekbook Ethernet Configuration

Ethernet interface:

    eno1

Adapter:

    Realtek RTL8111/8168

Kernel driver:

    r8169

The NetworkManager profile `netplan-eno1` was originally configured for IPv4 link-local addressing.

It was changed to DHCP:

    sudo nmcli connection modify netplan-eno1 ipv4.method auto
    sudo nmcli connection down netplan-eno1
    sudo nmcli connection up netplan-eno1

The Sleekbook then received:

    192.168.1.144/24

Terryza at 192.168.1.1 was reachable over the wired LAN.

## BIOS Configuration

The HP BIOS originally had:

    Internal Network Adapter Boot: Disabled

It was changed to:

    Internal Network Adapter Boot: Enabled

The F9 boot menu then provided:

- ubuntu (Samsung SSD 870 EVO 500GB)
- Network Adapter (IPv4 UEFI)
- Network Adapter (IPv6 UEFI)
- Boot From EFI File

For recovery, use:

    Network Adapter (IPv4 UEFI)

## Verified End-to-End PXE Recovery

The complete recovery path was physically tested:

    HP UEFI
      -> Realtek Ethernet
      -> Linksys LAN
      -> Terryza DHCP/TFTP
      -> ipxe.efi
      -> boot_script.ipxe
      -> Ubuntu recovery kernel
      -> custom recovery initramfs
      -> recovery shell

During the successful test:

- PXE server: 192.168.1.1
- iPXE downloaded successfully
- Sleekbook received 192.168.1.144
- boot_script.ipxe downloaded successfully
- recovery kernel downloaded successfully
- recovery initramfs loaded successfully
- r8169 Ethernet driver loaded
- recovery shell appeared

The recovery environment displayed:

    ========================================
     SLEEKBOOK NETWORK RECOVERY
    ========================================
    Recovery tools loaded.
    SSD will NOT be mounted automatically.

The SSD is deliberately not mounted automatically so a damaged/replacement disk can be handled manually.

The same recovery boot also reported:

    ata1: SATA link down (SStatus 0 SControl 300)

This provides additional evidence of the intermittent SATA connection problem.

## Recovery Capability

The Sleekbook can now boot into a recovery environment without a USB drive, provided:

1. Terryza is running.
2. Terryza is connected to the LAN.
3. The Sleekbook is connected to the LAN by Ethernet.
4. Internal Network Adapter Boot remains enabled in BIOS.
5. Network Adapter (IPv4 UEFI) is selected from the F9 boot menu.

The recovery environment has the tools required to access the Terryza SMB backup and rebuild/restore the Sleekbook disk.

Actual destructive disk restoration has NOT been tested and should only be performed after verifying the target disk and partition layout.

## Temperature Check After Recovery Test

After returning to normal Ubuntu, lm-sensors reported approximately:

- main PCI sensor: 54 C
- Radeon GPU: 52 C
- ACPI thermal zone: 53 C

The previous boot journal contained no recorded overheating, thermal-throttling, or critical-temperature event.

Linux had not been continuously logging sensor temperatures, so the maximum temperature reached during the PXE test is unknown.

## Current Status

As of 2026-09-27:

- Full recovery backup: COMPLETE
- Backup verification by FSArchiver: 0 reported filesystem errors
- Recovery metadata: SAVED
- Terryza PXE server: CONFIGURED
- UEFI PXE boot: TESTED
- Custom recovery environment: TESTED
- USB recovery media: NOT REQUIRED for this recovery path
- SSD SMART media health: PASSED
- SATA CRC errors: INCREASING
- Intermittent SATA link failure: CONFIRMED
- SATA cable/connector inspection or replacement: PENDING
- Exact SATA cable part number: NOT YET PHYSICALLY VERIFIED
