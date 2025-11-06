#include <stdio.h>
#include <unistd.h>

int main(int argc, char **argv) {
    printf("========================================\n");
    printf("  Hola Mundo!\n");
    printf("========================================\n");
    printf("\n");
    printf("Un poco de información:\n");
    
    system("uname -a");
    printf("\n");
    
    system("free -h");
    printf("\n");
    
    printf("Algunos comandos disponibles:\n");
    printf("  led_blink [delay_ms] - Blink LEDs\n");
    printf("  htop - Process monitor\n");
    printf("  lua\n");
    printf("\n");
    
    return 0;
}