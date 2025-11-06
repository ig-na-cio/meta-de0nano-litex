SUMMARY = "LED demo programs"
DESCRIPTION = "."
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI = " \
    file://led_blink.c \
    file://hello.c \
"

S = "${WORKDIR}"

do_compile() {
    ${CC} ${CFLAGS} ${LDFLAGS} led_blink.c -o led_blink
    ${CC} ${CFLAGS} ${LDFLAGS} hello.c -o hello
}

do_install() {
    install -d ${D}${bindir}
    install -m 0755 led_blink ${D}${bindir}/
    install -m 0755 hello ${D}${bindir}/
}

FILES:${PN} = " \
    ${bindir}/led_blink \
    ${bindir}/hello \
"