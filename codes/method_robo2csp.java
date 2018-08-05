public String translateRobo2CSP(String robomindProgram, String path) {
        String cspText = "";
		
		RobotCheckerReadConfig readConfig = new RobotCheckerReadConfig();
		try {
			languageFileDir = readConfig.getPropValues("spoofaxFile");
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		try (Spoofax spoofax = new Spoofax()) {
			FileObject languageDir = spoofax.resourceService.resolve("zip:" + RobotChecker.languageFileDir + "!/");
			ILanguageImpl compilerRobo = spoofax.languageDiscoveryService.languageFromDirectory(languageDir);

			FileObject fileToParse = spoofax.resourceService.resolve(path);
			String roboText = spoofax.sourceTextService.text(fileToParse);
			ISpoofaxInputUnit input = spoofax.unitService.inputUnit(fileToParse, roboText, compilerRobo, null);
			ISpoofaxParseUnit parseUnit = spoofax.syntaxService.parse(input);

			IOutline outlineCSP = spoofax.outlineService.outline(parseUnit);

		    cspText = outlineCSP.roots().toString();

		} catch (MetaborgException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return cspText;
	}