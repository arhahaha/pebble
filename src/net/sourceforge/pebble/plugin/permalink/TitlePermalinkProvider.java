/*
 * Copyright (c) 2003-2006, Simon Brown
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in
 *     the documentation and/or other materials provided with the
 *     distribution.
 *
 *   - Neither the name of Pebble nor the names of its contributors may
 *     be used to endorse or promote products derived from this software
 *     without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package net.sourceforge.pebble.plugin.permalink;

import net.sourceforge.pebble.domain.BlogEntry;
import net.sourceforge.pebble.domain.DailyBlog;

import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.List;

/**
 * Generates permalinks based upon the blog entry title. This implementation
 * only uses the following characters from the title:
 * <ul>
 * <li>a-z</li>
 * <li>A-Z</li>
 * <li>0-9</li>
 * <li>_ (underscore)</li>
 * </ul>
 * For titles without these characters (e.g. those using an extended character
 * set) the blog entry ID is used for the permalink instead.
 *
 * @author Simon Brown
 */
public class TitlePermalinkProvider extends PermalinkProviderSupport {

  /** the regex used to check for a blog entry permalink */
  private static final String BLOG_ENTRY_PERMALINK_REGEX = "/\\d\\d\\d\\d/\\d\\d/\\d\\d/[\\w]*.html";

  /**
   * Gets the permalink for a blog entry.
   *
   * @return  a URI as a String
   */
  public synchronized String getPermalink(BlogEntry blogEntry) {
    if (blogEntry.getTitle() == null || blogEntry.getTitle().length() == 0) {
      return buildPermalink(blogEntry) + ".html";
    } else {
      DailyBlog dailyBlog = blogEntry.getDailyBlog();
      List entries = dailyBlog.getEntries();
      int count = 0;
      for (int i = entries.size()-1; i > entries.indexOf(blogEntry); i--) {
        BlogEntry entry = (BlogEntry)entries.get(i);
        if (entry.getTitle().equals(blogEntry.getTitle())) {
          count++;
        }
      }

      if (count == 0) {
        return buildPermalink(blogEntry) + ".html";
      } else {
        return buildPermalink(blogEntry) + "_" + blogEntry.getId() + ".html";
      }
    }
  }

  private String buildPermalink(BlogEntry blogEntry) {
    String title = blogEntry.getTitle();
    if (title == null || title.length() == 0) {
      title = "" + blogEntry.getId();
    } else {
      title = title.toLowerCase();
      title = title.replaceAll("[\\. ,;/\\\\-]", "_");
      title = title.replaceAll("[^a-z0-9_]", "");
      title = title.replaceAll("_+", "_");
      title = title.replaceAll("^_*", "");
      title = title.replaceAll("_*$", "");
    }

    // if the title has been blanked out, use the blog entry instead
    if (title == null || title.length() == 0) {
      title = "" + blogEntry.getId();
    }

    DecimalFormat format = new DecimalFormat("00");
    int year = blogEntry.getDailyBlog().getMonthlyBlog().getYearlyBlog().getYear();
    int month = blogEntry.getDailyBlog().getMonthlyBlog().getMonth();
    int day = blogEntry.getDailyBlog().getDay();

    return "/" + year + "/" + format.format(month) + "/" +
        format.format(day) + "/" + title;
  }

  /**
   * Determines whether the specified URI is a blog entry permalink.
   *
   * @param uri   a relative URI
   * @return      true if the URI represents a permalink to a blog entry,
   *              false otherwise
   */
  public boolean isBlogEntryPermalink(String uri) {
    if (uri != null) {
      return uri.matches(BLOG_ENTRY_PERMALINK_REGEX);
    } else {
      return false;
    }
  }

  /**
   * Gets the blog entry referred to by the specified URI.
   *
   * @param uri   a relative URI
   * @return  a BlogEntry instance, or null if one can't be found
   */
  public BlogEntry getBlogEntry(String uri) {
    DailyBlog dailyBlog = getDailyBlog(uri);

    Iterator it = dailyBlog.getEntries().iterator();
    while (it.hasNext()) {
      BlogEntry blogEntry = (BlogEntry)it.next();
      // use the local permalink, just in case the entry has been aggregated
      // and an original permalink assigned
      if (blogEntry.getLocalPermalink().endsWith(uri)) {
        return blogEntry;
      }
    }

    return null;
  }

}
