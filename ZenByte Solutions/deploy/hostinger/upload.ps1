# Upload ZenByte project to Hostinger VPS from Windows
# Usage:
#   .\deploy\hostinger\upload.ps1 -ServerIp "123.45.67.89" -User "root"

param(
    [Parameter(Mandatory = $true)]
    [string]$ServerIp,

    [string]$User = "root",
    [string]$RemotePath = "/var/www/zenbyte"
)

$ProjectRoot = Split-Path (Split-Path $PSScriptRoot -Parent) -Parent
$Exclude = @("target", "node_modules", ".git", ".env")

Write-Host "Uploading from: $ProjectRoot"
Write-Host "Uploading to:   ${User}@${ServerIp}:${RemotePath}"

ssh "${User}@${ServerIp}" "mkdir -p $RemotePath"

# Use scp recursively (requires OpenSSH on Windows 10+)
scp -r "$ProjectRoot\frontend" "${User}@${ServerIp}:${RemotePath}/"
scp -r "$ProjectRoot\backend" "${User}@${ServerIp}:${RemotePath}/"
scp -r "$ProjectRoot\nodejs" "${User}@${ServerIp}:${RemotePath}/"
scp -r "$ProjectRoot\database" "${User}@${ServerIp}:${RemotePath}/"
scp -r "$ProjectRoot\deploy" "${User}@${ServerIp}:${RemotePath}/"
scp "$ProjectRoot\docker-compose.prod.yml" "${User}@${ServerIp}:${RemotePath}/"
scp "$ProjectRoot\docker-compose.yml" "${User}@${ServerIp}:${RemotePath}/"
scp "$ProjectRoot\package.json" "${User}@${ServerIp}:${RemotePath}/"
scp "$ProjectRoot\HOSTINGER_DEPLOY.md" "${User}@${ServerIp}:${RemotePath}/"

Write-Host ""
Write-Host "Upload complete. SSH in and run:"
Write-Host "  cd $RemotePath"
Write-Host "  cp deploy/hostinger/.env.example .env"
Write-Host "  nano .env"
Write-Host "  chmod +x deploy/hostinger/deploy.sh && ./deploy/hostinger/deploy.sh"
