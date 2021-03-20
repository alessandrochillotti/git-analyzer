package logic;

import java.io.*;
import java.util.logging.Logger;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.NoHeadException;
import org.eclipse.jgit.api.errors.TransportException;
import org.eclipse.jgit.revwalk.RevCommit;

public class GitAnalyzer {

	private static final String URL = "https://github.com/alessandrochillotti/helloworld-git-analysis.git";
	private static final String PATH = "C:\\Users\\aless\\github-sandboxes\\git-analysis-jgit";
	private static final String STRING_TO_FOUND = "Added";
	private static final Logger LOGGER = Logger.getLogger("Commit ID");
	
	public static void main(String[] args)
			throws IOException, InvalidRemoteException, TransportException, GitAPIException {

		GitAnalyzer rc = new GitAnalyzer();
		rc.getCommitID(rc.getGit(URL, PATH), STRING_TO_FOUND);
	}

	public Git getGit(String url, String pathDir) throws InvalidRemoteException, TransportException, GitAPIException, IOException {

		File dir = new File(pathDir);
		Git git;

		// If the directory is not empty, then I refresh the directory
		if (dir.list().length == 0) {
			git = Git.cloneRepository().setURI(url).setDirectory(dir).call();
		} else {
			git = Git.open(dir);
			git.pull();
			git.checkout();
		}

		return git;
	}

	public void getCommitID(Git git, String stringToFound) throws NoHeadException, GitAPIException {

		// Get log of commits
		Iterable<RevCommit> log = git.log().call();

		// Print all commit that containt the word STRING_TO_FOUND
		for (RevCommit element : log) {
			String commentCommit = element.getFullMessage();
			if (commentCommit.contains(stringToFound)) {
				LOGGER.info(element.getName());
			}
		}
	}
}