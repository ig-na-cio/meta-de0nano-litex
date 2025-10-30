SUMMARY = "Linux kernel with Litex support"
DESCRIPTION = "Linux kernel with Litex patches and generated files"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"

inherit kernel
# require recipes-kernel/linux/linux-yocto.inc

LINUX_VERSION = "6.9"
LINUX_VERSION_EXTENSION = "-litex"

SRCREV = "v6.9"
SRC_URI = "git://git.kernel.org/pub/scm/linux/kernel/git/stable/linux.git;branch=linux-6.9.y;protocol=https"

SRC_URI += " \
    file://defconfig \
    file://de0nano.dts \
    file://0001-LiteX-LiteEth-add-polling-support.patch \
    file://0002-LiteSDCard-re-introduce-gpio-based-card-detection.patch \
    file://0003-LiteX-driver-for-LiteSPI.patch \
    file://0004-LiteX-add-defconfig-files-for-linux-on-litex-rocket-.patch \
    file://0005-litex.h-FIXME-restore-variable-length-accessors.patch \
    file://0006-LiteX-driver-for-LiteGPIO-interface.patch \
    file://0007-gpio-litex-Make-the-irqchip-immutable.patch \
    file://0008-LiteX-driver-for-I2CMaster.patch \
    file://0009-LiteX-driver-for-XADC-hwmon.patch \
    file://0010-LiteX-driver-for-PWM.patch \
    file://0011-pwm-litex-Don-t-check-the-return-code-of-pwmchip_rem.patch \
    file://0012-pwm-litex-Migrate-to-pwm_ops.apply.patch \
    file://0013-LiteX-driver-for-SPI-Flash-mtd-device.patch \
    file://0014-LiteX-driver-for-ICAPBitstream-fpga-manager.patch \
    file://0015-fpga-litex-Update-for-Use-standard-dev_release-for-c.patch \
    file://0016-LiteX-support-for-VexRiscV-interrupt-controller.patch \
    file://0017-LiteX-driver-for-LiteVideo.patch \
    file://0018-LiteX-driver-for-MMCM.patch \
    file://0019-LiteX-LiteSATA-block-driver.patch \
    file://0020-pwm-litex-fix_pwm_ops_and_base.patch \
"

S = "${WORKDIR}/git"

COMPATIBLE_MACHINE = "de0nano-litex"
KERNEL_IMAGETYPE = "Image"

# Copiar DTS al del kernel
do_configure:prepend() {
    mkdir -p ${S}/arch/riscv/boot/dts/litex
    cp ${WORKDIR}/de0nano.dts ${S}/arch/riscv/boot/dts/litex/
    
    if [ ! -f ${S}/arch/riscv/boot/dts/litex/Makefile ]; then
        echo 'dtb-y += de0nano.dtb' > ${S}/arch/riscv/boot/dts/litex/Makefile
    fi
    
    if ! grep -q "litex" ${S}/arch/riscv/boot/dts/Makefile 2>/dev/null; then
        echo 'subdir-y += litex' >> ${S}/arch/riscv/boot/dts/Makefile
    fi
}