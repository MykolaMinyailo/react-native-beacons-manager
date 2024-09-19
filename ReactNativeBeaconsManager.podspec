require 'json'

package = JSON.parse(File.read(File.join(__dir__, 'package.json')))

Pod::Spec.new do |s|
  s.name         = "ReactNativeBeaconsManager"
  s.version      = package['version']
  s.summary      = package['description']
  s.homepage     = package['homepage']
  s.license      = { :type => "MIT" }
  s.authors      = package['author']
  s.platform     = :ios, "8.0"
  s.source       = { :path => "." }
  s.source_files = "ios", "ios/**/*.{h,m}"

  s.dependency 'React'
end
